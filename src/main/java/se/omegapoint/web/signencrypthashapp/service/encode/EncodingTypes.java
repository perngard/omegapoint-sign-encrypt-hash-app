package se.omegapoint.web.signencrypthashapp.service.encode;

public enum EncodingTypes {
    BASE64("BASE64"),
    HEX("HEX"),
    URL("URL"),
    ASCII("ASCII"),
    ;

    private final String hash;

    private EncodingTypes(final String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return hash;
    }
}

