package se.omegapoint.web.signencrypthashapp;

import se.omegapoint.web.signencrypthashapp.vo.EncryptVO;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class Utils {

    public static String bytesToHex(byte[] bytes) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xff & bytes[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static byte[] hextoBytes(String hex, int length){
        byte[] bytes = new BigInteger(hex,16).toByteArray();

        if(bytes.length % 2 != 0) {
            bytes = Arrays.copyOfRange(bytes, 1, length+1);
        }

        return bytes;
    }

    public static String hextoBase64String(String hex){
        return Base64.getEncoder().encodeToString(hextoBytes(hex, hex.length()/2));
    }

    public static String toBase64String(byte[] hex){
        return Base64.getEncoder().encodeToString(hex);
    }
}
