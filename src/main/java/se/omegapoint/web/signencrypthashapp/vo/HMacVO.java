package se.omegapoint.web.signencrypthashapp.vo;

import se.omegapoint.web.signencrypthashapp.common.TextType;

public class HMacVO {

    private String text;
    private String secret;
    private String algorithm;
    private String compareValue;
    private String compareValueType = TextType.HEX.toString();
    private String secretType = TextType.TEXT.toString();

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getCompareValue() {
        return compareValue;
    }

    public void setCompareValue(String compareValue) {
        this.compareValue = compareValue;
    }

    public String getCompareValueType() {
        return compareValueType;
    }

    public void setCompareValueType(String compareValueType) {
        this.compareValueType = compareValueType;
    }

    public String getSecretType() {
        return secretType;
    }

    public void setSecretType(String secretType) {
        this.secretType = secretType;
    }

}
