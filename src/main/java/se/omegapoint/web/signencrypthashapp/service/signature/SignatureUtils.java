package se.omegapoint.web.signencrypthashapp.service.signature;

import se.omegapoint.web.signencrypthashapp.common.TextType;
import se.omegapoint.web.signencrypthashapp.common.Utils;
import se.omegapoint.web.signencrypthashapp.vo.CryptoVO;
import se.omegapoint.web.signencrypthashapp.vo.ResponseVO;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Arrays;
import java.util.List;

public class SignatureUtils {

    public static ResponseVO encrypt(CryptoVO cryptoVO, int divider, List<String> types, List<String> paddings) throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        ResponseVO respVO;
        String algorithm = cryptoVO.getAlgorithm();
        String type = cryptoVO.getType();
        String padding = cryptoVO.getPadding();
        String initVector = cryptoVO.getInitVector();
        String encryptionAlgorithm = algorithm+"/"+type+"/"+padding;

        if (types.stream().anyMatch(s -> s.equals(type)) && paddings.stream().anyMatch(s -> s.equals(padding))) {

            Cipher cipher = Cipher.getInstance(encryptionAlgorithm);
            int ivSize = cipher.getBlockSize();
            if (type.equalsIgnoreCase("ECB")){
                cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(cryptoVO, divider));
            } else{
                byte[] iv = new byte[ivSize];
                if(initVector == null || initVector.isEmpty()) {
                    SecureRandom random = new SecureRandom();
                    random.nextBytes(iv);
                } else {
                    iv = convertToByte(initVector, cryptoVO.getInitVectorType());
                }
                System.out.println("IV (hex)    : "+ toString(iv, TextType.HEX.toString()));
                System.out.println("IV (base64) : "+ toString(iv, TextType.BASE64.toString()));
                cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(cryptoVO, divider), new IvParameterSpec(iv));
            }

            byte[] encryptedBytes = cipher.doFinal(convertToByte(cryptoVO.getClearText(),cryptoVO.getClearTextType()));
            respVO = compare(cryptoVO.getCipherText(), cryptoVO.getCipherTextType(), encryptedBytes);

        } else {
            throw new IllegalArgumentException("No support for " + encryptionAlgorithm);
        }

        return respVO;
    }

    public static ResponseVO decrypt(CryptoVO cryptoVO, int divider, List<String> types, List<String> paddings) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        ResponseVO respVO;
        String algorithm = cryptoVO.getAlgorithm();
        String type = cryptoVO.getType();
        String padding = cryptoVO.getPadding();
        String initVector = cryptoVO.getInitVector();
        String encryptionAlgorithm = algorithm+"/"+type+"/"+padding;

        if (types.stream().anyMatch(s -> s.equals(type)) && paddings.stream().anyMatch(s -> s.equals(padding))) {

            Cipher cipher = Cipher.getInstance(encryptionAlgorithm);
            int ivSize = cipher.getBlockSize();
            if (type.equalsIgnoreCase("ECB")){
                cipher.init(Cipher.DECRYPT_MODE, getSecretKey(cryptoVO, divider));
            } else{
                if(initVector == null || initVector.isEmpty()) {
                    throw new IllegalArgumentException("InitVector cannot be empty");
                } else {
                    byte[] iv = convertToByte(initVector, cryptoVO.getInitVectorType());
                    System.out.println("IV (hex)    : "+ toString(iv, TextType.HEX.toString()));
                    System.out.println("IV (base64) : "+ toString(iv, TextType.BASE64.toString()));
                    cipher.init(Cipher.DECRYPT_MODE, getSecretKey(cryptoVO, divider), new IvParameterSpec(iv));
                }
            }

            byte[] clearTextBytes = cipher.doFinal(convertToByte(cryptoVO.getCipherText(), cryptoVO.getCipherTextType()));
            respVO = compare(cryptoVO.getClearText(), cryptoVO.getClearTextType(), clearTextBytes);

        } else {
            throw new IllegalArgumentException("No support for " + encryptionAlgorithm);
        }

        return respVO;
    }

    private static Key getSecretKey(CryptoVO cryptoVO, int divider) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        int keyLength = Integer.parseInt(cryptoVO.getKeyLength());
        Key secretKey =  null;

        MessageDigest sha = null;
        byte[] key = convertToByte(cryptoVO.getSecret(), cryptoVO.getSecretType());
        sha = MessageDigest.getInstance("SHA-512");
        key = sha.digest(key);
        key = Arrays.copyOf(key, keyLength/divider);
        System.out.println("SecretKey (hex)    : "+toString(key,TextType.HEX.toString()));
        System.out.println("SecretKey (base64) : "+toString(key,TextType.BASE64.toString()));
        secretKey = new SecretKeySpec(key, cryptoVO.getAlgorithm());

        return secretKey;

    }

    private static ResponseVO compare(String compareValue, String compareValueType, byte[] calculatedBytes) throws UnsupportedEncodingException {
        ResponseVO respVO = new ResponseVO();

        if(compareValue == null || compareValue.isEmpty()){
            if(compareValueType.equalsIgnoreCase(TextType.TEXT.toString())) {
                respVO.setText(toString(calculatedBytes, TextType.TEXT.toString()));
                System.out.println("Text value        : " + respVO.getText());
            } else {
                respVO.setHex(toString(calculatedBytes, TextType.HEX.toString()));
                respVO.setBase64(toString(calculatedBytes, TextType.BASE64.toString()));

                System.out.println("Hex value        : " + respVO.getHex());
                System.out.println("Base64 value     : " + respVO.getBase64());
            }
        } else {
            byte[] compareValueBytes = convertToByte(compareValue, compareValueType);
            respVO.setCompare(Boolean.toString(Arrays.equals(calculatedBytes, compareValueBytes)));

            System.out.println("Calculated Value ("+compareValueType.toLowerCase()+")  : " + toString(compareValueBytes, compareValueType));
            System.out.println("Compare Value    ("+compareValueType.toLowerCase()+")  : " + compareValue);
        }

        return respVO;
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
        System.out.println("Converted IV Size : "+convertedValue.length);

        return convertedValue;
    }
}
