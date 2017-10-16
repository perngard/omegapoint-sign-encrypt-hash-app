package se.omegapoint.web.signencrypthashapp.service.signature;

import com.fasterxml.jackson.annotation.JsonInclude;
import se.omegapoint.web.signencrypthashapp.service.crypto.CryptoAlgorithms;
import se.omegapoint.web.signencrypthashapp.service.crypto.symmetric.AES;
import se.omegapoint.web.signencrypthashapp.service.crypto.symmetric.Blowfish;
import se.omegapoint.web.signencrypthashapp.service.crypto.symmetric.DES;
import se.omegapoint.web.signencrypthashapp.service.crypto.symmetric.DESede;
import se.omegapoint.web.signencrypthashapp.vo.CryptoVO;
import se.omegapoint.web.signencrypthashapp.vo.ResponseVO;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignatureHandler {

    String text;
    String hex;
    String base64;
    String compare;

    public SignatureHandler(CryptoVO cryptoVO, boolean encrypt) throws NoSuchPaddingException, IOException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        if(encrypt){
            encrypt(cryptoVO);
        } else {
            decrypt(cryptoVO);
        }
    }

    public String getCompare() {
        return compare;
    }

    public String getText() {
        return text;
    }

    public String getHex() {
        return hex;
    }

    public String getBase64() {
        return base64;
    }

    private void encrypt(CryptoVO cryptoVO) throws NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        ResponseVO respVO = new ResponseVO();
        String algorithm = cryptoVO.getAlgorithm();
        String encryptionAlgorithm = algorithm+"/"+cryptoVO.getType()+"/"+cryptoVO.getPadding();
        String keyLength = cryptoVO.getKeyLength();

        if (Arrays.stream(CryptoAlgorithms.values()).anyMatch(s -> s.name().equalsIgnoreCase(algorithm)) ) {
            System.out.println(algorithm + " encryption.");
            System.out.println("Ciper used is " + encryptionAlgorithm + " with a " + keyLength + " bits key.");

            if (algorithm.equalsIgnoreCase(CryptoAlgorithms.AES.toString())) {
                AES aes = new AES();
                respVO = aes.encrypt(cryptoVO);
            } else if (algorithm.equalsIgnoreCase(CryptoAlgorithms.DES.toString())) {
                DES des = new DES();
                respVO = des.encrypt(cryptoVO);
            } else if (algorithm.equalsIgnoreCase(CryptoAlgorithms.DESede.toString())) {
                DESede desede = new DESede();
                respVO = desede.encrypt(cryptoVO);
            } else if (algorithm.equalsIgnoreCase(CryptoAlgorithms.BLOWFISH.toString())) {
                Blowfish blowfish = new Blowfish();
                respVO = blowfish.encrypt(cryptoVO);
            }

            compare = respVO.getCompare();
            hex = respVO.getHex();
            base64 = respVO.getBase64();

        }else {
            throw new IllegalArgumentException(algorithm+" not supported");
        }

    }

    private void decrypt(CryptoVO cryptoVO) throws NoSuchPaddingException, InvalidAlgorithmParameterException, IOException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        ResponseVO respVO = new ResponseVO();
        String algorithm = cryptoVO.getAlgorithm();
        String encryptionAlgorithm = algorithm+"/"+cryptoVO.getType()+"/"+cryptoVO.getPadding();
        String keyLength = cryptoVO.getKeyLength();

        if (Arrays.stream(CryptoAlgorithms.values()).anyMatch(s -> s.name().equalsIgnoreCase(algorithm)) ) {
            System.out.println(algorithm + " decryption.");
            System.out.println("Ciper used is " + encryptionAlgorithm + " with a " + keyLength + " bits key.");

            if (algorithm.equalsIgnoreCase(CryptoAlgorithms.AES.toString())) {
                AES aes = new AES();
                respVO = aes.decrypt(cryptoVO);
            } else if (algorithm.equalsIgnoreCase(CryptoAlgorithms.DES.toString())) {
                DES des = new DES();
                respVO = des.decrypt(cryptoVO);
            } else if (algorithm.equalsIgnoreCase(CryptoAlgorithms.DESede.toString())) {
                DESede desede = new DESede();
                respVO = desede.decrypt(cryptoVO);
            } else if (algorithm.equalsIgnoreCase(CryptoAlgorithms.BLOWFISH.toString())) {
                Blowfish blowfish = new Blowfish();
                respVO = blowfish.decrypt(cryptoVO);
            }

            compare = respVO.getCompare();
            text = respVO.getText();

        }else {
            throw new IllegalArgumentException(algorithm+" not supported");
        }

    }

}
