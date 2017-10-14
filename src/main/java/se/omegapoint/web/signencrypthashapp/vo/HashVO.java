package se.omegapoint.web.signencrypthashapp.vo;

import org.springframework.web.bind.annotation.RequestParam;

public class HashVO {

    private String text;
    private String algorithm;
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

}
