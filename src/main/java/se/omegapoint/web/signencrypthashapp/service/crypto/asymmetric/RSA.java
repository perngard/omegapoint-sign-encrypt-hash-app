package se.omegapoint.web.signencrypthashapp.service.crypto.asymmetric;

import se.omegapoint.web.signencrypthashapp.service.crypto.CryptoUtils;
import se.omegapoint.web.signencrypthashapp.vo.CryptoVO;
import se.omegapoint.web.signencrypthashapp.vo.ResponseVO;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Arrays;
import java.util.List;

public class RSA {

    private final List<String> types = Arrays.asList("ECB");
    private final List<String> paddings = Arrays.asList("PKCS1Padding", "OAEPWithSHA-1AndMGF1Padding", "OAEPWithSHA-256AndMGF1Padding");

    public ResponseVO encrypt(CryptoVO cryptoVO) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        return CryptoUtils.encrypt(cryptoVO, 1, types, paddings);
    }

    public ResponseVO decrypt(CryptoVO cryptoVO) throws NoSuchPaddingException, IOException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        return CryptoUtils.decrypt(cryptoVO, 1, types, paddings);
    }

}
