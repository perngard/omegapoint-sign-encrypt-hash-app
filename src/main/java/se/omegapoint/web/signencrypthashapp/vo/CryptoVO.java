package se.omegapoint.web.signencrypthashapp.vo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.web.signencrypthashapp.common.TextType;

@JsonAutoDetect
public class CryptoVO {

    private String clearText;
    private String algorithm;
    private String keyLength;
    private String type;
    private String padding;
    private String secret;
    private String initVector;
    private String cipherText;
    private String clearTextType = TextType.TEXT.toString();
    private String secretType = TextType.TEXT.toString();
    private String initVectorType = TextType.HEX.toString();
    private String cipherTextType = TextType.HEX.toString();

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

    @JsonProperty(required = true)
    public String getKeyLength() {
        return keyLength;
    }

    @JsonProperty(required = true)
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

    public String getCipherText() {
        return cipherText;
    }

    public void setCipherText(String cipherText) {
        this.cipherText = cipherText;
    }

    public String getClearTextType() {
        return clearTextType;
    }

    public void setClearTextType(String clearTextType) {
        this.clearTextType = clearTextType;
    }

    public String getCipherTextType() {
        return cipherTextType;
    }

    public void setCipherTextType(String cipherTextType) {
        this.cipherTextType = cipherTextType;
    }

    public String getSecretType() {
        return secretType;
    }

    public void setSecretType(String secretType) {
        this.secretType = secretType;
    }

    public String getInitVectorType() {
        return initVectorType;
    }

    public void setInitVectorType(String initVectorType) {
        this.initVectorType = initVectorType;
    }
}
