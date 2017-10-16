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

    public static String toString(byte[] bytes, String type) throws UnsupportedEncodingException {
        String convertedValue;

        if (type.equalsIgnoreCase(TextType.TEXT.toString())) {
            convertedValue = Utils.byteToString(bytes);
        } else if (type.equalsIgnoreCase(TextType.BASE64.toString())) {
            convertedValue = Utils.bytesToBase64(bytes);
        } else if (type.equalsIgnoreCase(TextType.HEX.toString())) {
            convertedValue = Utils.bytesToHex(bytes);

        } else {
            throw new IllegalArgumentException(type + " not supported");
        }

        return convertedValue;
    }

    public static byte[] convertToByte(String value, String type) throws UnsupportedEncodingException {
        byte[] convertedValue;

        if (type.equalsIgnoreCase(TextType.TEXT.toString())) {
            convertedValue = value.getBytes("UTF-8");
        } else if (type.equalsIgnoreCase(TextType.BASE64.toString())) {
            convertedValue = Utils.base64toBytes(value);
        } else if (type.equalsIgnoreCase(TextType.HEX.toString())) {
            convertedValue = Utils.hexToBytes(value);

        } else {
            throw new IllegalArgumentException(type + " not supported");
        }

        return convertedValue;
    }

    public static String printBytes(byte[] bytes){
        StringBuilder sb = new StringBuilder();

        for(byte value: bytes){
            sb.append(DatatypeConverter.printByte(value));
            sb.append(" ");
        }

        return "(byte) ["+bytes.length+"] : {"+sb.toString().trim()+"}";
    }
}
