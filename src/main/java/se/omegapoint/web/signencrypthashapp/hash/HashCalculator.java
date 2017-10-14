package se.omegapoint.web.signencrypthashapp.hash;

import se.omegapoint.web.signencrypthashapp.Utils;
import se.omegapoint.web.signencrypthashapp.vo.HashVO;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public class HashCalculator {

    private final List<String> algorithms = Arrays.asList("SHA-1", "SHA-256", "SHA-384", "SHA-512");

    boolean correct = false;

    public HashCalculator(HashVO hashVO) throws NoSuchAlgorithmException {
        calculateHash(hashVO);
    }

    public boolean isCorrect() {
        return correct;
    }

    private void calculateHash(HashVO hashVO) throws NoSuchAlgorithmException {

        String algorithm = hashVO.getAlgorithm();
        String compareValue = hashVO.getCompareValue();

        if (algorithms.stream().anyMatch(s -> s.equals(algorithm))) {

            System.out.println("algorithm: "+algorithm);
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] hash = md.digest(hashVO.getText().getBytes(StandardCharsets.UTF_8));
            String result = Utils.bytesToHex(hash);
            System.out.println("Hash         : "+result);
            System.out.println("Compared with: "+compareValue);
            System.out.println("Hash (base64)         : "+Utils.base64String(result));
            System.out.println("Compared with (base64): "+Utils.base64String(compareValue));

            correct = result.equalsIgnoreCase(compareValue);

        } else {
            throw new IllegalArgumentException("No support for " + algorithm);
        }
    }



}
