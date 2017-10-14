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

    public static byte[] HextoByte(String hex, int length){
        byte[] bytes = new BigInteger(hex,16).toByteArray();

        if(bytes.length % 2 != 0) {
            bytes = Arrays.copyOfRange(bytes, 1, length+1);
        }

        return bytes;
    }

    public static String base64String(String hex){
        return Base64.getEncoder().encodeToString(HextoByte(hex, hex.length()/2));
    }

    public static boolean encrypt(EncryptVO encryptVO, Key secretKey, int ivSize, List<String> types, List<String> paddings) throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        boolean correct;
        String algorithm = encryptVO.getAlgorithm();
        String type = encryptVO.getType();
        String padding = encryptVO.getPadding();
        String initVector = encryptVO.getInitVector();
        String secret = encryptVO.getSecret();
        String encryptedText = encryptVO.getEncryptedText();
        int keyLength = Integer.parseInt(encryptVO.getKeyLength());
        String encryptionAlgorithm = algorithm+"/"+type+"/"+padding;

        if (types.stream().anyMatch(s -> s.equals(type)) && paddings.stream().anyMatch(s -> s.equals(padding))) {

            Cipher cipher = Cipher.getInstance(encryptionAlgorithm);
            if (type.equalsIgnoreCase(types.get(0))){
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            } else{
                byte[] iv = new byte[ivSize];
                if(initVector.isEmpty()) {
                    SecureRandom random = new SecureRandom();
                    random.nextBytes(iv);
                    System.out.println("IV: "+bytesToHex(iv));
                    System.out.println("IV (base64): "+base64String(Utils.bytesToHex(iv)));
                } else {
                    System.out.println("IV: "+initVector);
                    System.out.println("IV (base64): "+base64String(initVector));
                    iv = Utils.HextoByte(initVector, ivSize);
                }

                cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));

            }

            String encryptedString = bytesToHex(cipher.doFinal(encryptVO.getClearText().getBytes("UTF-8")));

            correct = encryptedString.equalsIgnoreCase(encryptedText);
            System.out.println("encryptedText: "+encryptedString);
            System.out.println("Compared with: "+encryptedText);
            System.out.println("encryptedText (base64): "+base64String(encryptedString));
            System.out.println("Compared with (base64): "+base64String(encryptedText));
        } else {
            throw new IllegalArgumentException("No support for " + encryptionAlgorithm);
        }

        return correct;
    }
}
