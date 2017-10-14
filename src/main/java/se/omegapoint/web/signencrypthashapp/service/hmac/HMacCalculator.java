package se.omegapoint.web.signencrypthashapp.service.hmac;

import com.fasterxml.jackson.annotation.JsonInclude;
import se.omegapoint.web.signencrypthashapp.common.TextType;
import se.omegapoint.web.signencrypthashapp.common.Utils;
import se.omegapoint.web.signencrypthashapp.vo.HMacVO;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HMacCalculator {
    String hex;
    String base64;
    String compare;

    public HMacCalculator(HMacVO hMacVO) throws NoSuchAlgorithmException, InvalidKeyException {
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

    private void calculateHMac(HMacVO hMacVO) throws NoSuchAlgorithmException, InvalidKeyException {
        String algorithm = hMacVO.getAlgorithm();

        if (Arrays.stream(HMacs.values()).anyMatch(s -> s.name().equalsIgnoreCase(algorithm))) {

            System.out.println("algorithm: "+algorithm);
            Mac hmac = Mac.getInstance(algorithm);
            byte[] secretBytes = hMacVO.getSecret().getBytes(StandardCharsets.UTF_8);
            SecretKeySpec secretKey = new SecretKeySpec(secretBytes, "HmacSHA256");
            hmac.init(secretKey);
            byte[] hashBytes = hmac.doFinal(hMacVO.getText().getBytes(StandardCharsets.UTF_8));

            compare(hMacVO, hashBytes);

        } else {
            throw new IllegalArgumentException("No support for " + algorithm);
        }
    }

    private void compare(HMacVO hMacVO, byte[] hashBytes) {

        if(hMacVO.getCompareValue() == null || hMacVO.getCompareValue().isEmpty()){
            hex = Utils.bytesToHex(hashBytes);
            base64 = Utils.toBase64String(hashBytes);

            System.out.println("Hex value        : " + hex);
            System.out.println("Base64 value     : " + base64);
        } else {
            String calculatedHash;
            if (hMacVO.getCompareType().equalsIgnoreCase(TextType.BASE64.toString())){
                calculatedHash = Utils.toBase64String(hashBytes);
            } else {
                calculatedHash = Utils.bytesToHex(hashBytes);
            }
            compare = Boolean.toString(calculatedHash.equalsIgnoreCase(hMacVO.getCompareValue()));

            System.out.println("HMac ("+hMacVO.getCompareType().toLowerCase()+")          : " + calculatedHash);
            System.out.println("Compare HMac ("+hMacVO.getCompareType().toLowerCase()+")  : " + hMacVO.getCompareValue());

        }

    }

}
