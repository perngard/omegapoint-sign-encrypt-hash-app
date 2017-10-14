package se.omegapoint.web.signencrypthashapp.service.encrypt.crypto;

import se.omegapoint.web.signencrypthashapp.vo.EncryptVO;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Arrays;
import java.util.List;

public class RSA {

    private final List<String> types = Arrays.asList();
    private final List<String> paddings = Arrays.asList("PKCS1Padding", "OAEPWithSHA-1AndMGF1Padding", "OAEPWithSHA-256AndMGF1Padding");

    boolean correct = false;

    public RSA(EncryptVO encryptVO) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
    }

    public boolean isCorrect() {
        return correct;
    }

}