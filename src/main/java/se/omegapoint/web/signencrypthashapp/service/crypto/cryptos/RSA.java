package se.omegapoint.web.signencrypthashapp.service.crypto.cryptos;

import se.omegapoint.web.signencrypthashapp.vo.CryptoVO;

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

    public RSA(CryptoVO cryptoVO) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
    }

    public boolean isCorrect() {
        return correct;
    }

}
