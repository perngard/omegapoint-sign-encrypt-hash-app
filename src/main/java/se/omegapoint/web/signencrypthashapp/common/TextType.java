package se.omegapoint.web.signencrypthashapp.common;

public enum TextType {
    TEXT("TEXT"),
    BASE64("BASE64"),
    HEX("HEX"),
    ;

    private final String text;

    private TextType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
