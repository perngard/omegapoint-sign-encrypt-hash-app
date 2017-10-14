package se.omegapoint.web.signencrypthashapp.hash;

import com.fasterxml.jackson.annotation.JsonInclude;
import se.omegapoint.web.signencrypthashapp.TextType;
import se.omegapoint.web.signencrypthashapp.Utils;
import se.omegapoint.web.signencrypthashapp.vo.HashVO;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HashCalculator {

    private final List<String> algorithms = Arrays.asList("SHA-1", "SHA-224", "SHA-256", "SHA-384", "SHA-512");

    String hex;
    String base64;
    String compare;

    public HashCalculator(HashVO hashVO) throws NoSuchAlgorithmException {
        calculateHash(hashVO);
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

    private void calculateHash(HashVO hashVO) throws NoSuchAlgorithmException {

        String algorithm = hashVO.getAlgorithm();

        if (algorithms.stream().anyMatch(s -> s.equals(algorithm))) {

            System.out.println("algorithm: "+algorithm);
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] hashBytes = md.digest(hashVO.getText().getBytes(StandardCharsets.UTF_8));

            compare(hashVO, hashBytes);

        } else {
            throw new IllegalArgumentException("No support for " + algorithm);
        }
    }

    private void compare(HashVO hashVO, byte[] hashBytes) {

        if(hashVO.getCompareValue() == null || hashVO.getCompareValue().isEmpty()){
            hex = Utils.bytesToHex(hashBytes);
            base64 = Utils.toBase64String(hashBytes);

            System.out.println("Hex value        : " + hex);
            System.out.println("Base64 value     : " + base64);
        } else {
            String calculatedHash;
            if (hashVO.getCompareType().equalsIgnoreCase(TextType.BASE64.toString())){
                calculatedHash = Utils.toBase64String(hashBytes);
            } else {
                calculatedHash = Utils.bytesToHex(hashBytes);
            }
            compare = Boolean.toString(calculatedHash.equalsIgnoreCase(hashVO.getCompareValue()));

            System.out.println("Hash ("+hashVO.getCompareType().toLowerCase()+")          : " + calculatedHash);
            System.out.println("Compare Hash ("+hashVO.getCompareType().toLowerCase()+")  : " + hashVO.getCompareValue());

        }

    }


}
