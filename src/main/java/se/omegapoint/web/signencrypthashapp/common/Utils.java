package se.omegapoint.web.signencrypthashapp.common;

import javax.validation.constraints.NotNull;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Base64;

public class Utils {

    public static String byteToString(byte[] bytes) throws UnsupportedEncodingException {
        return new String(bytes, "UTF-8");
    }

    public static String bytesToHex(byte[] bytes) {
        return  DatatypeConverter.printHexBinary(bytes).toLowerCase();
    }

    public static byte[] hexToBytes(@NotNull String hex){
        hex = hex.replace(" ","");
        return DatatypeConverter.parseHexBinary(hex);
    }

    public static byte[] base64toBytes(@NotNull String base64) throws UnsupportedEncodingException {
        return Base64.getDecoder().decode(base64);
    }

    public static String bytesToBase64(byte[] bytes) throws UnsupportedEncodingException {
        return Base64.getEncoder().encodeToString(bytes);
    }
}
