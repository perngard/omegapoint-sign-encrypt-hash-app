package se.omegapoint.web.signencrypthashapp.encrypt;

import se.omegapoint.web.signencrypthashapp.encrypt.crypto.AES;
import se.omegapoint.web.signencrypthashapp.encrypt.crypto.Blowfish;
import se.omegapoint.web.signencrypthashapp.encrypt.crypto.DES;
import se.omegapoint.web.signencrypthashapp.encrypt.crypto.DESede;
import se.omegapoint.web.signencrypthashapp.vo.EncryptVO;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public class cryptoHandler {

    private final List<String> algorithms = Arrays.asList("AES", "DES", "3DES", "Blowfish");

    boolean correct = false;

    public cryptoHandler(EncryptVO encryptVO) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        calculate(encryptVO);
    }

    public boolean isCorrect() {
        return correct;
    }

    private void calculate(EncryptVO encryptVO) throws NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        String algorithm = encryptVO.getAlgorithm();
        String encryptionAlgorithm = algorithm+"/"+encryptVO.getType()+"/"+encryptVO.getPadding();
        String keyLength = encryptVO.getKeyLength();

        if (algorithms.stream().anyMatch(s -> s.equals(algorithm)) ) {
            System.out.println(algorithm + " encryption.");
            System.out.println("Ciper used is " + encryptionAlgorithm + " with a " + keyLength + " bits key.");


            if (algorithm.equalsIgnoreCase(algorithms.get(0))) {
                AES encrypt = new AES(encryptVO);
                correct = encrypt.isCorrect();
            } else if (algorithm.equalsIgnoreCase(algorithms.get(1))) {
                DES encrypt = new DES(encryptVO);
                correct = encrypt.isCorrect();
            } else if (algorithm.equalsIgnoreCase(algorithms.get(2))) {
                encryptVO.setAlgorithm("DESede");
                DESede encrypt = new DESede(encryptVO);
                correct = encrypt.isCorrect();
            } else if (algorithm.equalsIgnoreCase(algorithms.get(3))) {
                Blowfish encrypt = new Blowfish(encryptVO);
                correct = encrypt.isCorrect();
            }
        }else {
            throw new IllegalArgumentException(algorithm+" not supported");
        }

    }
}
