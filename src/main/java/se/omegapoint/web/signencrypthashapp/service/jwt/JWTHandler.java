package se.omegapoint.web.signencrypthashapp.service.jwt;

import com.fasterxml.jackson.annotation.JsonInclude;
import se.omegapoint.web.signencrypthashapp.common.TextType;
import se.omegapoint.web.signencrypthashapp.common.Utils;
import se.omegapoint.web.signencrypthashapp.vo.JWTTokenVO;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JWTHandler {
    String signature;
    String header;
    String data;
    String token;
    String verified;

    public JWTHandler(JWTTokenVO tokenVO, boolean create) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        if(create){
            createToken(tokenVO);
        } else {
            verifyToken(tokenVO);
        }

    }

    public String getVerified() {
        return verified;
    }

    public String getHeader() {
        return header;
    }

    public String getData() {
        return data;
    }

    public String getToken() {
        return token;
    }

    private void createToken(JWTTokenVO tokenVO) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        String algorithm = tokenVO.getAlgorithm();

        if (Arrays.stream(JWTAlgorithms.values()).anyMatch(s -> s.name().equalsIgnoreCase(algorithm))) {
            System.out.println("algorithm: "+algorithm);
            String header = buildHeader(algorithm);
            String data = tokenVO.getData();
            String textToSign = base64UrlEncode(header) + "." + base64UrlEncode(data);
            String signature;

            if(algorithm.equalsIgnoreCase(JWTAlgorithms.NONE.toString())) {
                signature = "";
            } else {
                Mac hmac = Mac.getInstance(String.valueOf(JWTAlgorithms.valueOf(algorithm)));
                byte[] secretBytes = Utils.convertToByte(tokenVO.getSecret(), tokenVO.getSecretType());
                SecretKeySpec secretKey = new SecretKeySpec(secretBytes, "HmacSHA256");
                hmac.init(secretKey);
                System.out.println("SecretKey " + Utils.printBytes(secretBytes));
                System.out.println("SecretKey (hex)       : " + Utils.toString(secretBytes, TextType.HEX.toString()));
                System.out.println("SecretKey (base64)    : " + Utils.toString(secretBytes, TextType.BASE64.toString()));

                byte[] signatureBytes = hmac.doFinal(textToSign.getBytes(StandardCharsets.UTF_8));
                signature = base64UrlEncode(signatureBytes);
            }
            token = textToSign+"."+signature;

        } else {
            throw new IllegalArgumentException("No support for " + algorithm);
        }
    }

    private void verifyToken(JWTTokenVO tokenVO) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        String token = tokenVO.getToken();
        String[] parts = token.split("\\.");
        String textToSign = parts[0] + "." + parts[1];
        header = base64UrlDecode(parts[0]);
        data = base64UrlDecode(parts[1]);
        String algorithm = getAlgorithmFromHeader(header);

        if (Arrays.stream(JWTAlgorithms.values()).anyMatch(s -> s.name().equalsIgnoreCase(algorithm))) {
            System.out.println("algorithm: "+algorithm);
            byte[] calculatedBytes;

            if(algorithm.equalsIgnoreCase(JWTAlgorithms.NONE.toString())) {
                verified = "Unsecure JWT - No signature to verify";
            } else {
                byte[] signatureBytes = base64UrlByteDecode(parts[2]);

                Mac hmac = Mac.getInstance(String.valueOf(JWTAlgorithms.valueOf(algorithm)));
                byte[] secretBytes = Utils.convertToByte(tokenVO.getSecret(), tokenVO.getSecretType());
                SecretKeySpec secretKey = new SecretKeySpec(secretBytes, "HmacSHA256");
                hmac.init(secretKey);
                System.out.println("SecretKey " + Utils.printBytes(secretBytes));
                System.out.println("SecretKey (hex)       : " + Utils.toString(secretBytes, TextType.HEX.toString()));
                System.out.println("SecretKey (base64)    : " + Utils.toString(secretBytes, TextType.BASE64.toString()));

                calculatedBytes = hmac.doFinal(textToSign.getBytes(StandardCharsets.UTF_8));
                System.out.println("Calculated Signature " + Utils.printBytes(calculatedBytes));
                System.out.println("Given Signature      " + Utils.printBytes(signatureBytes));

                verified = Boolean.toString(Arrays.equals(calculatedBytes, signatureBytes));
            }

        } else {
            throw new IllegalArgumentException("No support for " + algorithm);
        }

    }

    private String getAlgorithmFromHeader(String header) {
        String[] parts = header.replace(" ","").split("alg\":\"");
        return parts[1].split("\"")[0];
    }

    private String buildHeader(String algorithm) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("\"alg\" : \"");
        sb.append(algorithm);
        sb.append("\",\n");
        sb.append("\"typ\" : \"JWT\"\n");
        sb.append("}");

        return sb.toString();
    }

    private String base64UrlEncode(byte[] value) throws UnsupportedEncodingException {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(value);
    }

    private byte[] base64UrlByteDecode(String value) throws UnsupportedEncodingException {
        return Base64.getUrlDecoder().decode(value.getBytes(StandardCharsets.UTF_8));
    }

    private String base64UrlEncode(String value) throws UnsupportedEncodingException {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    private String base64UrlDecode(String value) throws UnsupportedEncodingException {
        return new String(Base64.getUrlDecoder().decode(value), StandardCharsets.UTF_8);
    }
}
