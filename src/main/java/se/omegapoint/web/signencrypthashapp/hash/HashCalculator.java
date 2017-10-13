package se.omegapoint.web.signencrypthashapp.hash;

import se.omegapoint.web.signencrypthashapp.Utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public class HashCalculator {

    private final List<String> algorithms = Arrays.asList("SHA-1", "SHA-256", "SHA-384", "SHA-512");

    boolean correct = false;

    public HashCalculator(String text, String algorithm, String compareHash){
        calculateHash(text, algorithm, compareHash);
    }

    public boolean isCorrect() {
        return correct;
    }

    private void calculateHash(String text, String algorithm, String compareHash){

        if (algorithms.stream().anyMatch(s -> s.equals(algorithm))) {
            try {
                MessageDigest md = MessageDigest.getInstance(algorithm);
                byte[] hash = md.digest(text.getBytes(StandardCharsets.UTF_8));
                String result = Utils.bytesToHex(hash);

                correct = result.equalsIgnoreCase(compareHash);

            } catch (NoSuchAlgorithmException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }



}
