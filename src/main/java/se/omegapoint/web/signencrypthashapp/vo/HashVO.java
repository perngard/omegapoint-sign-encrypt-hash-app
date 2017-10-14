package se.omegapoint.web.signencrypthashapp.vo;

import org.springframework.web.bind.annotation.RequestParam;
import se.omegapoint.web.signencrypthashapp.TextType;

public class HashVO {

    private String text;
    private String algorithm;
    private String compareType = TextType.HEX.toString();
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

    public String getCompareType() {
        return compareType;
    }

    public void setCompareType(String compareType) {
        this.compareType = compareType;
    }

}
