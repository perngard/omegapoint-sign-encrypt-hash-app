package se.omegapoint.web.signencrypthashapp.service.crypto.cryptos;

import se.omegapoint.web.signencrypthashapp.common.Utils;
import se.omegapoint.web.signencrypthashapp.service.crypto.CryptoUtils;
import se.omegapoint.web.signencrypthashapp.vo.CryptoVO;
import se.omegapoint.web.signencrypthashapp.vo.ResponseVO;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Arrays;
import java.util.List;

public class DESede {

    private final List<String> types = Arrays.asList("ECB", "CBC");
    private final List<String> paddings = Arrays.asList("NoPadding", "PKCS5Padding");

    public ResponseVO encrypt(CryptoVO cryptoVO) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        return CryptoUtils.encrypt(cryptoVO, 7, types, paddings);
    }

    public ResponseVO decrypt(CryptoVO cryptoVO) throws NoSuchPaddingException, IOException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        return CryptoUtils.decrypt(cryptoVO, 7, types, paddings);
    }

}
