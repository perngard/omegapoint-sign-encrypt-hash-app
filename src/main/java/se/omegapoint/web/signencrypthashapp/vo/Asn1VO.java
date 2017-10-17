package se.omegapoint.web.signencrypthashapp.vo;

import se.omegapoint.web.signencrypthashapp.common.TextType;

public class Asn1VO {

    private String text;
    private String asn1;
    private String type = TextType.BASE64.toString();
    private boolean verbose = false;
    private String compareValue;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAsn1() {
        return asn1;
    }

    public void setAsn1(String asn1) {
        this.asn1 = asn1;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompareValue() {
        return compareValue;
    }

    public void setCompareValue(String compareValue) {
        this.compareValue = compareValue;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
}
