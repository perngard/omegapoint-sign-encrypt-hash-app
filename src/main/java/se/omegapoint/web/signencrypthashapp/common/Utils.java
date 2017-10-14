package se.omegapoint.web.signencrypthashapp.common;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Base64;

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

    public static byte[] hextoBytes(String hex){
        return hextoBytes(hex, hex.length()/2);
    }

    public static String hextoBase64String(String hex){
        return Base64.getEncoder().encodeToString(hextoBytes(hex, hex.length()/2));
    }

    public static String base64Decoder(String base64) throws UnsupportedEncodingException {
        return new String(Base64.getDecoder().decode(base64),"UTF-8");
    }

    public static String toBase64String(byte[] hex){
        return Base64.getEncoder().encodeToString(hex);
    }
}
