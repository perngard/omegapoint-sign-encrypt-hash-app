package se.omegapoint.web.signencrypthashapp.encrypt.crypto;

import se.omegapoint.web.signencrypthashapp.Utils;
import se.omegapoint.web.signencrypthashapp.vo.EncryptVO;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Arrays;
import java.util.List;

public class RSA {

    private final List<String> types = Arrays.asList();
    private final List<String> paddings = Arrays.asList("PKCS1Padding", "OAEPWithSHA-1AndMGF1Padding", "OAEPWithSHA-256AndMGF1Padding");

    boolean correct = false;

    public RSA(EncryptVO encryptVO) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        calculate(encryptVO);
    }

    public boolean isCorrect() {
        return correct;
    }

    private void calculate(EncryptVO encryptVO) throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        String algorithm = encryptVO.getAlgorithm();
        String type = encryptVO.getType();
        String padding = encryptVO.getPadding();
        String initVector = encryptVO.getInitVector();
        String secret = encryptVO.getSecret();
        String encryptedText = encryptVO.getEncryptedText();
        int keyLength = Integer.parseInt(encryptVO.getKeyLength());
        Key secretKey = getSecretKey(secret, keyLength);
        String encryptionAlgorithm = algorithm+"/"+type+"/"+padding;

        if (types.stream().anyMatch(s -> s.equals(type)) && paddings.stream().anyMatch(s -> s.equals(padding))) {
            System.out.println("Algorithm: " + encryptionAlgorithm);

            Cipher cipher = Cipher.getInstance(encryptionAlgorithm);
            if (type.equalsIgnoreCase(types.get(0))){
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            } else{
                int ivSize = 8;
                byte[] iv = new byte[ivSize];
                if(initVector.isEmpty()) {
                    SecureRandom random = new SecureRandom();
                    random.nextBytes(iv);
                    System.out.println("IV: "+Utils.bytesToHex(iv));
                    System.out.println("IV (base64): "+Utils.base64String(Utils.bytesToHex(iv)));
                } else {
                    System.out.println("IV: "+initVector);
                    System.out.println("IV (base64): "+Utils.base64String(initVector));
                    iv = Utils.HextoByte(initVector, ivSize);
                }

                cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));

            }

            String encryptedString = Utils.bytesToHex(cipher.doFinal(encryptVO.getClearText().getBytes("UTF-8")));

            correct = encryptedString.equalsIgnoreCase(encryptedText);
            System.out.println("encryptedText: "+encryptedString);
            System.out.println("Compared with: "+encryptedText);
            System.out.println("encryptedText (base64): "+Utils.base64String(encryptedString));
            System.out.println("Compared with (base64): "+Utils.base64String(encryptedText));
        } else {
            throw new IllegalArgumentException("No support for " + encryptionAlgorithm);
        }
    }

    private Key getSecretKey(String secret, int keyLength) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Key secretKey =  null;

        MessageDigest sha = null;
        byte[] key = secret.getBytes("UTF-8");
        System.out.println("keyLength: "+keyLength);
        sha = MessageDigest.getInstance("SHA-512");
        key = sha.digest(key);
        key = Arrays.copyOf(key, keyLength/8);
        System.out.println("SecretKey: "+Utils.bytesToHex(key));
        System.out.println("SecretKey (base64): "+Utils.base64String(Utils.bytesToHex(key)));
        secretKey = new SecretKeySpec(key, "AES");

        return secretKey;

    }

}
