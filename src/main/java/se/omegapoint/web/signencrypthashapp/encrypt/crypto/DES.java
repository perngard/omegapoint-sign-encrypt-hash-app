package se.omegapoint.web.signencrypthashapp.encrypt.crypto;

import se.omegapoint.web.signencrypthashapp.Utils;
import se.omegapoint.web.signencrypthashapp.encrypt.CryptoUtils;
import se.omegapoint.web.signencrypthashapp.vo.EncryptVO;
import se.omegapoint.web.signencrypthashapp.vo.ResponseVO;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Arrays;
import java.util.List;

public class DES {


    private final List<String> types = Arrays.asList("ECB", "CBC");
    private final List<String> paddings = Arrays.asList("NoPadding", "PKCS5Padding");

    ResponseVO respVO;

    public DES(EncryptVO encryptVO) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        respVO = CryptoUtils.encrypt(encryptVO, getSecretKey(encryptVO), 8, types, paddings);
    }

    public ResponseVO getResponseVO() {
        return respVO;
    }

    private Key getSecretKey(EncryptVO encryptVO) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        int keyLength = Integer.parseInt(encryptVO.getKeyLength());
        Key secretKey =  null;

        MessageDigest sha = null;
        byte[] key = encryptVO.getSecret().getBytes("UTF-8");
        System.out.println("keyLength: "+keyLength);
        sha = MessageDigest.getInstance("SHA-512");
        key = sha.digest(key);
        key = Arrays.copyOf(key, keyLength/7);
        System.out.println("SecretKey: "+Utils.bytesToHex(key));
        System.out.println("SecretKey (base64): "+Utils.hextoBase64String(Utils.bytesToHex(key)));
        secretKey = new SecretKeySpec(key, "DES");

        return secretKey;

    }

}
