package se.omegapoint.web.signencrypthashapp.service.hash;

import com.fasterxml.jackson.annotation.JsonInclude;
import se.omegapoint.web.signencrypthashapp.common.TextType;
import se.omegapoint.web.signencrypthashapp.common.Utils;
import se.omegapoint.web.signencrypthashapp.vo.HashVO;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HashCalculator {

    String hex;
    String base64;
    String compare;

    public HashCalculator(HashVO hashVO) throws NoSuchAlgorithmException, UnsupportedEncodingException {
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

    private void calculateHash(HashVO hashVO) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        String algorithm = HashAlgorithms.valueOf(hashVO.getAlgorithm()).toString();

        if (Arrays.stream(HashAlgorithms.values()).anyMatch(s -> s.name().equalsIgnoreCase(hashVO.getAlgorithm()))) {

            System.out.println("algorithm: "+algorithm);
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] hashBytes = md.digest(hashVO.getText().getBytes(StandardCharsets.UTF_8));

            compare(hashVO, hashBytes);

        } else {
            throw new IllegalArgumentException("No support for " + algorithm);
        }
    }

    private void compare(HashVO hashVO, byte[] hashBytes) throws UnsupportedEncodingException {

        if(hashVO.getCompareValue() == null || hashVO.getCompareValue().isEmpty()){
            hex = Utils.bytesToHex(hashBytes);
            base64 = Utils.bytesToBase64(hashBytes);

            System.out.println("Hex value        : " + hex);
            System.out.println("Base64 value     : " + base64);
        } else {
            String calculatedHash;
            if (hashVO.getCompareValueType().equalsIgnoreCase(TextType.BASE64.toString())){
                calculatedHash = Utils.bytesToBase64(hashBytes);
            } else {
                calculatedHash = Utils.bytesToHex(hashBytes);
            }
            compare = Boolean.toString(calculatedHash.equalsIgnoreCase(hashVO.getCompareValue()));

            System.out.println("Hash ("+hashVO.getCompareValueType().toLowerCase()+")          : " + calculatedHash);
            System.out.println("Compare Hash ("+hashVO.getCompareValueType().toLowerCase()+")  : " + hashVO.getCompareValue());

        }

    }


}
