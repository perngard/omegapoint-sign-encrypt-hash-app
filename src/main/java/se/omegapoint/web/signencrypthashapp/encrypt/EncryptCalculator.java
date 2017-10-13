package se.omegapoint.web.signencrypthashapp.encrypt;

import se.omegapoint.web.signencrypthashapp.Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class EncryptCalculator {


    private final List<String> algorithms = Arrays.asList("AES/ECB/PKCS5Padding", "AES/CBC/PKCS5Padding", "AES/CFB/PKCS5Padding", "AES/OFB/PKCS5Padding");

    boolean correct = false;

    public EncryptCalculator(String clearText, String algorithm, String keyLength, String type, String padding, String secret, String encryptedText){
        calculateAES(clearText, algorithm, keyLength, type, padding, secret, encryptedText);
    }

    public boolean isCorrect() {
        return correct;
    }

    private void calculateAES(String clearText, String algorithm, String keyLength, String type, String padding, String secret, String encryptedText){

        String encryptionAlgorithm = algorithm+"/"+type+"/"+padding;
        System.out.println("Algorithm: " + encryptionAlgorithm);

        if (algorithms.stream().anyMatch(s -> s.equals(encryptionAlgorithm))) {
            try
            {
                Cipher cipher = Cipher.getInstance(encryptionAlgorithm);
                cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(secret, Integer.parseInt(keyLength)));

                String encryptedString = Utils.bytesToHex(cipher.doFinal(clearText.getBytes("UTF-8")));
                System.out.println("encryptedText: "+encryptedString);
                correct = encryptedString.equalsIgnoreCase(encryptedText);
                System.out.println("Compared with: "+encryptedText);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private Key getSecretKey(String secret, int keyLength) {
        Key secretKey =  null;
        try {
            MessageDigest sha = null;
            byte[] key = secret.getBytes("UTF-8");
            System.out.println(keyLength);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, keyLength/8);
            System.out.println("SecretKey: "+Utils.bytesToHex(key));
            secretKey = new SecretKeySpec(key, "AES");


        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return secretKey;

    }

}
