package se.omegapoint.web.signencrypthashapp.vo;

import se.omegapoint.web.signencrypthashapp.common.TextType;

public class HashVO {

    private String text;
    private String algorithm;
    private String compareValueType = TextType.HEX.toString();
    private String compareValue;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

}
