package se.omegapoint.web.signencrypthashapp.hmac;

import se.omegapoint.web.signencrypthashapp.Utils;
import se.omegapoint.web.signencrypthashapp.vo.HMacVO;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public class HMacCalculator {
    private final List<String> algorithms = Arrays.asList("HmacSHA1", "HmacSHA256");

    boolean correct = false;

    public HMacCalculator(HMacVO hMacVO) throws NoSuchAlgorithmException, InvalidKeyException {
        calculateHMac(hMacVO);
    }

    public boolean isCorrect() {
        return correct;
    }

    private void calculateHMac(HMacVO hMacVO) throws NoSuchAlgorithmException, InvalidKeyException {
        String algorithm = hMacVO.getAlgorithm();
        String compareValue = hMacVO.getCompareValue();

        if (algorithms.stream().anyMatch(s -> s.equals(algorithm))) {

            System.out.println("algorithm: "+algorithm);
            Mac hmac = Mac.getInstance(algorithm);
            byte[] secretBytes = hMacVO.getSecret().getBytes(StandardCharsets.UTF_8);
            SecretKeySpec secretKey = new SecretKeySpec(secretBytes, "HmacSHA256");
            hmac.init(secretKey);
            byte[] hash = hmac.doFinal(hMacVO.getText().getBytes(StandardCharsets.UTF_8));
            String result = Utils.bytesToHex(hash);
            System.out.println("HMac         : "+result);
            System.out.println("Compared with: "+compareValue);
            System.out.println("HMac (base64)         : "+Utils.base64String(result));
            System.out.println("Compared with (base64): "+Utils.base64String(compareValue));

            correct = result.equalsIgnoreCase(compareValue);

        } else {
            throw new IllegalArgumentException("No support for " + algorithm);
        }
    }

}
