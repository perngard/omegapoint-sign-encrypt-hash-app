package se.omegapoint.web.signencrypthashapp.service.hmac;

import com.fasterxml.jackson.annotation.JsonInclude;
import se.omegapoint.web.signencrypthashapp.common.TextType;
import se.omegapoint.web.signencrypthashapp.common.Utils;
import se.omegapoint.web.signencrypthashapp.vo.HMacVO;
import se.omegapoint.web.signencrypthashapp.vo.ResponseVO;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HMacCalculator {
    String hex;
    String base64;
    String compare;

    public HMacCalculator(HMacVO hMacVO) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        calculateHMac(hMacVO);
    }

    public String getCompare() {
        return compare;
    }

    public String getHex() {
        return hex;
    }

    public String getBase64() {
        return base64;
    }

    private void calculateHMac(HMacVO hMacVO) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        String algorithm = hMacVO.getAlgorithm();

        if (Arrays.stream(HMacAlgortihms.values()).anyMatch(s -> s.name().equalsIgnoreCase(algorithm))) {

            System.out.println("algorithm: "+algorithm);
            Mac hmac = Mac.getInstance(algorithm);
            byte[] secretBytes = convertToByte(hMacVO.getSecret(), hMacVO.getSecretType());
            SecretKeySpec secretKey = new SecretKeySpec(secretBytes, "HmacSHA256");
            hmac.init(secretKey);
            byte[] hashBytes = hmac.doFinal(hMacVO.getText().getBytes(StandardCharsets.UTF_8));

            compare(hMacVO.getCompareValue(), hMacVO.getCompareValueType(), hashBytes);

        } else {
            throw new IllegalArgumentException("No support for " + algorithm);
        }
    }

    private void compare(String compareValue, String compareValueType, byte[] calculatedBytes) throws UnsupportedEncodingException {

        if(compareValue == null || compareValue.isEmpty()){
                hex = toString(calculatedBytes, TextType.HEX.toString());
                base64 = toString(calculatedBytes, TextType.BASE64.toString());

                System.out.println("Hex value        : " + hex);
                System.out.println("Base64 value     : " + base64);
        } else {
            byte[] compareValueBytes = convertToByte(compareValue, compareValueType);
            compare = Boolean.toString(Arrays.equals(calculatedBytes, compareValueBytes));

            System.out.println("Calculated Value ("+compareValueType.toLowerCase()+")  : " + toString(compareValueBytes, compareValueType));
            System.out.println("Compare Value    ("+compareValueType.toLowerCase()+")  : " + compareValue);
        }
    }

    public static String toString(byte[] bytes, String type) throws UnsupportedEncodingException {
        String convertedValue;

        if (type.equalsIgnoreCase("TEXT")) {
            convertedValue = Utils.byteToString(bytes);
        } else if (type.equalsIgnoreCase("BASE64")) {
            convertedValue = Utils.bytesToBase64(bytes);
        } else if (type.equalsIgnoreCase("HEX")) {
            convertedValue = Utils.bytesToHex(bytes);

        } else {
            throw new IllegalArgumentException(type + " not supported");
        }

        return convertedValue;
    }

    public static byte[] convertToByte(String value, String type) throws UnsupportedEncodingException {
        byte[] convertedValue;

        if (type.equalsIgnoreCase("TEXT")) {
            convertedValue = value.getBytes("UTF-8");
        } else if (type.equalsIgnoreCase("BASE64")) {
            convertedValue = Utils.base64toBytes(value);
        } else if (type.equalsIgnoreCase("HEX")) {
            convertedValue = Utils.hexToBytes(value);

        } else {
            throw new IllegalArgumentException(type + " not supported");
        }

        return convertedValue;
    }

}
