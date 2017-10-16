package se.omegapoint.web.signencrypthashapp.service.hash;

import com.fasterxml.jackson.annotation.JsonInclude;
import se.omegapoint.web.signencrypthashapp.common.TextType;
import se.omegapoint.web.signencrypthashapp.common.Utils;
import se.omegapoint.web.signencrypthashapp.vo.HashVO;

import javax.xml.bind.DatatypeConverter;
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

    private void compare(HashVO hashVO, byte[] calculatedBytes) throws UnsupportedEncodingException {

        if(hashVO.getCompareValue() == null || hashVO.getCompareValue().isEmpty()){
            hex = Utils.bytesToHex(calculatedBytes);
            base64 = Utils.bytesToBase64(calculatedBytes);

            System.out.println("Hex value        : " + hex);
            System.out.println("Base64 value     : " + base64);
        } else {
            byte[] compareValueBytes = Utils.convertToByte(hashVO.getCompareValue(), hashVO.getCompareValueType());
            compare = Boolean.toString(Arrays.equals(calculatedBytes, compareValueBytes));

            System.out.println("Calculated Value " + Utils.printBytes(calculatedBytes));
            System.out.println("Compare Value    " + Utils.printBytes(compareValueBytes));
            System.out.println("Hash ("+hashVO.getCompareValueType().toLowerCase()+")               : " + Utils.toString(calculatedBytes,hashVO.getCompareValueType()));
            System.out.println("Compare Hash ("+hashVO.getCompareValueType().toLowerCase()+")       : " + hashVO.getCompareValue());

        }

    }

}
