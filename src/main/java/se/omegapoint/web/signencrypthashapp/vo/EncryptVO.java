package se.omegapoint.web.signencrypthashapp.vo;

import org.springframework.web.bind.annotation.RequestParam;
import se.omegapoint.web.signencrypthashapp.TextType;

public class EncryptVO {
    String clearText;
    String algorithm;
    String keyLength;
    String type;
    String padding;
    String secret;
    String initVector;
    private String encryptedTextType = TextType.HEX.toString();
    String encryptedText;

    public String getClearText() {
        return clearText;
    }

    public void setClearText(String clearText) {
        this.clearText = clearText;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getKeyLength() {
        return keyLength;
    }

    public void setKeyLength(String keyLength) {
        this.keyLength = keyLength;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPadding() {
        return padding;
    }

    public void setPadding(String padding) {
        this.padding = padding;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getInitVector() {
        return initVector;
    }

    public void setInitVector(String initVector) {
        this.initVector = initVector;
    }

    public String getEncryptedText() {
        return encryptedText;
    }

    public void setEncryptedText(String encryptedText) {
        this.encryptedText = encryptedText;
    }

    public String getEncryptedTextType() {
        return encryptedTextType;
    }

    public void setEncryptedTextType(String encryptedTextType) {
        this.encryptedTextType = encryptedTextType;
    }
}
