package se.omegapoint.web.signencrypthashapp.service.crypto;

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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Arrays;
import java.util.List;

public class CryptoUtils {

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
                    System.out.println("IV (hex)    : "+ Utils.bytesToHex(iv));
                    System.out.println("IV (base64) : "+ Utils.hextoBase64String(Utils.bytesToHex(iv)));
                } else {
                    System.out.println("IV (hex)    : "+initVector);
                    System.out.println("IV (base64) : "+ Utils.hextoBase64String(initVector));
                    iv = toIVByteType(initVector, ivSize, cryptoVO.getInitVectorType());;
                }
                cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(cryptoVO, divider), new IvParameterSpec(iv));
            }

            byte[] encryptedBytes = cipher.doFinal(cryptoVO.getClearText().getBytes("UTF-8"));
            respVO = compare(cryptoVO.getCipherText(), encryptedBytes, cryptoVO.getCipherTextType());

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
                    System.out.println("IV (hex)    : "+initVector);
                    System.out.println("IV (base64) : "+ Utils.hextoBase64String(initVector));
                    byte[] iv = toIVByteType(initVector, ivSize, cryptoVO.getInitVectorType());
                    cipher.init(Cipher.DECRYPT_MODE, getSecretKey(cryptoVO, divider), new IvParameterSpec(iv));
                }
            }

            byte[] encryptedBytes;
            if(cryptoVO.getCipherTextType().equalsIgnoreCase(TextType.BASE64.toString())) {
                encryptedBytes = Utils.base64Decoder(cryptoVO.getCipherText()).getBytes("UTF-8");
            } else {
                encryptedBytes = Utils.hextoBytes(cryptoVO.getCipherText(), cryptoVO.getCipherText().length()/2);
            }

            byte[] clearTextBytes = cipher.doFinal(encryptedBytes);
            respVO = compare(cryptoVO.getClearText(), clearTextBytes, TextType.TEXT.toString());

        } else {
            throw new IllegalArgumentException("No support for " + encryptionAlgorithm);
        }

        return respVO;
    }

    private static Key getSecretKey(CryptoVO cryptoVO, int divider) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        int keyLength = Integer.parseInt(cryptoVO.getKeyLength());
        Key secretKey =  null;

        MessageDigest sha = null;
        byte[] key = CryptoUtils.toSecretByteType(cryptoVO.getSecret(), cryptoVO.getSecretType());
        sha = MessageDigest.getInstance("SHA-512");
        key = sha.digest(key);
        key = Arrays.copyOf(key, keyLength/divider);
        System.out.println("SecretKey (hex)    : "+Utils.bytesToHex(key));
        System.out.println("SecretKey (base64) : "+Utils.hextoBase64String(Utils.bytesToHex(key)));
        secretKey = new SecretKeySpec(key, cryptoVO.getAlgorithm());

        return secretKey;

    }

    private static IvParameterSpec readIV(final int ivSizeBytes, final InputStream is) throws IOException {
        final byte[] iv = new byte[ivSizeBytes];
        int offset = 0;
        while (offset < ivSizeBytes) {
            final int read = is.read(iv, offset, ivSizeBytes - offset);
            if (read == -1) {
                throw new IOException("Too few bytes for IV in input stream");
            }
            offset += read;
        }
        return new IvParameterSpec(iv);
    }

    private static ResponseVO compare(String compareValue, byte[] calculatedBytes, String type) throws UnsupportedEncodingException {
        ResponseVO respVO = new ResponseVO();

        if(compareValue == null || compareValue.isEmpty()){
            if(type.equalsIgnoreCase(TextType.TEXT.toString())) {
                respVO.setText(new String (calculatedBytes, "UTF-8"));

                System.out.println("Text value        : " + respVO.getText());
            } else {
                respVO.setHex(Utils.bytesToHex(calculatedBytes));
                respVO.setBase64(Utils.toBase64String(calculatedBytes));

                System.out.println("Hex value        : " + respVO.getHex());
                System.out.println("Base64 value     : " + respVO.getBase64());
            }
        } else {
            String encryptedString = toTextStringType(new String(calculatedBytes,"UTF-8"), type);
            respVO.setCompare(Boolean.toString(encryptedString.equalsIgnoreCase(compareValue)));

            System.out.println("Calculated Value ("+type.toLowerCase()+")  : " + encryptedString);
            System.out.println("Compare Value    ("+type.toLowerCase()+")  : " + compareValue);
        }

        return respVO;
    }

    public static String toTextStringType(String value, String type) throws UnsupportedEncodingException {
        String convertedValue;

        if (type.equalsIgnoreCase(TextType.TEXT.toString())) {
            convertedValue = value;
        } else if (type.equalsIgnoreCase(TextType.BASE64.toString())) {
            convertedValue = Utils.toBase64String(value.getBytes("UTF-8"));
        } else if (type.equalsIgnoreCase(TextType.HEX.toString())) {
            convertedValue = Utils.bytesToHex(value.getBytes("UTF-8"));

        } else {
            throw new IllegalArgumentException(type + " not supported");
        }

        return convertedValue;
    }

    public static byte[] toSecretByteType(String value, String type) throws UnsupportedEncodingException {
        return toTextStringType(value, type).getBytes("UTF-8");
    }

    public static byte[] toIVByteType(String value, int ivSize, String type) throws UnsupportedEncodingException {
        byte[] convertedValue;

        if (type.equalsIgnoreCase(TextType.TEXT.toString())) {
            convertedValue = value.getBytes("UTF-8");
        } else if (type.equalsIgnoreCase(TextType.BASE64.toString())) {
            convertedValue = Utils.base64Decoder(value).getBytes("UTF-8");
        } else if (type.equalsIgnoreCase(TextType.HEX.toString())) {
            convertedValue = Utils.hextoBytes(value, ivSize);

        } else {
            throw new IllegalArgumentException(type + " not supported");
        }
        System.out.println("IV Size : "+convertedValue.length);

        return convertedValue;
    }
}
