package se.omegapoint.web.signencrypthashapp.service.encrypt;

import com.fasterxml.jackson.annotation.JsonInclude;
import se.omegapoint.web.signencrypthashapp.service.encrypt.crypto.AES;
import se.omegapoint.web.signencrypthashapp.service.encrypt.crypto.Blowfish;
import se.omegapoint.web.signencrypthashapp.service.encrypt.crypto.DES;
import se.omegapoint.web.signencrypthashapp.service.encrypt.crypto.DESede;
import se.omegapoint.web.signencrypthashapp.vo.EncryptVO;
import se.omegapoint.web.signencrypthashapp.vo.ResponseVO;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CryptoHandler {

    String hex;
    String base64;
    String compare;

    public CryptoHandler(EncryptVO encryptVO) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        calculate(encryptVO);
    }

    public String getCompare() {
        return compare;
    }

    public String getHex() {
        return hex;
    }

    public String getBase64() {
        return base64;
    }

    private void calculate(EncryptVO encryptVO) throws NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        ResponseVO respVO = new ResponseVO();
        String algorithm = encryptVO.getAlgorithm();
        String encryptionAlgorithm = algorithm+"/"+encryptVO.getType()+"/"+encryptVO.getPadding();
        String keyLength = encryptVO.getKeyLength();

        if (Arrays.stream(Cryptos.values()).anyMatch(s -> s.name().equalsIgnoreCase(algorithm)) ) {
            System.out.println(algorithm + " encryption.");
            System.out.println("Ciper used is " + encryptionAlgorithm + " with a " + keyLength + " bits key.");


            if (algorithm.equalsIgnoreCase(Cryptos.AES.toString())) {
                AES encrypt = new AES(encryptVO);
                respVO = encrypt.getResponseVO();
            } else if (algorithm.equalsIgnoreCase(Cryptos.DES.toString())) {
                DES encrypt = new DES(encryptVO);
                respVO = encrypt.getResponseVO();
            } else if (algorithm.equalsIgnoreCase(Cryptos.DESede.toString())) {
                DESede encrypt = new DESede(encryptVO);
                respVO = encrypt.getResponseVO();
            } else if (algorithm.equalsIgnoreCase(Cryptos.BLOWFISH.toString())) {
                Blowfish encrypt = new Blowfish(encryptVO);
                respVO = encrypt.getResponseVO();
            }

            compare = respVO.getCompare();
            hex = respVO.getHex();
            base64 = respVO.getBase64();

        }else {
            throw new IllegalArgumentException(algorithm+" not supported");
        }

    }
}
