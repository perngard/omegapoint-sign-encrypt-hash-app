package se.omegapoint.web.signencrypthashapp.service.encrypt;

import se.omegapoint.web.signencrypthashapp.common.TextType;
import se.omegapoint.web.signencrypthashapp.common.Utils;
import se.omegapoint.web.signencrypthashapp.vo.EncryptVO;
import se.omegapoint.web.signencrypthashapp.vo.ResponseVO;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.List;

public class CryptoUtils {

    public static ResponseVO encrypt(EncryptVO encryptVO, Key secretKey, int ivSize, List<String> types, List<String> paddings) throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        ResponseVO respVO;
        String algorithm = encryptVO.getAlgorithm();
        String type = encryptVO.getType();
        String padding = encryptVO.getPadding();
        String initVector = encryptVO.getInitVector();
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
                    System.out.println("IV: "+ Utils.bytesToHex(iv));
                    System.out.println("IV (base64): "+ Utils.hextoBase64String(Utils.bytesToHex(iv)));
                } else {
                    System.out.println("IV: "+initVector);
                    System.out.println("IV (base64): "+ Utils.hextoBase64String(initVector));
                    iv = Utils.hextoBytes(initVector, ivSize);
                }

                cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));

            }

            byte[] encryptedBytes = cipher.doFinal(encryptVO.getClearText().getBytes("UTF-8"));
            respVO = compare(encryptVO, encryptedBytes);

        } else {
            throw new IllegalArgumentException("No support for " + encryptionAlgorithm);
        }

        return respVO;
    }

    private static ResponseVO compare(EncryptVO encryptVO, byte[] encryptedBytes) {
        ResponseVO respVO = new ResponseVO();

        if(encryptVO.getEncryptedText() == null || encryptVO.getEncryptedText().isEmpty()){
            respVO.setHex(Utils.bytesToHex(encryptedBytes));
            respVO.setBase64(Utils.toBase64String(encryptedBytes));

            System.out.println("Hex value        : " + respVO.getHex());
            System.out.println("Base64 value     : " + respVO.getBase64());
        } else {
            String encryptedString;
            if (encryptVO.getEncryptedTextType().equalsIgnoreCase(TextType.BASE64.toString())){
                encryptedString = Utils.toBase64String(encryptedBytes);
                respVO.setCompare(Boolean.toString(encryptedString.equalsIgnoreCase(encryptVO.getEncryptedText())));
            } else {
                encryptedString = Utils.bytesToHex(encryptedBytes);
                respVO.setCompare(Boolean.toString(encryptedString.equalsIgnoreCase(encryptVO.getEncryptedText())));
            }

            System.out.println("Hash ("+encryptVO.getEncryptedTextType().toLowerCase()+")          : " + encryptedString);
            System.out.println("Compare Hash ("+encryptVO.getEncryptedTextType().toLowerCase()+")  : " + encryptVO.getEncryptedText());

        }

        return respVO;
    }
}
