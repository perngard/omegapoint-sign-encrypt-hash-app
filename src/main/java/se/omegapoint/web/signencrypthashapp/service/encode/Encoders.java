package se.omegapoint.web.signencrypthashapp.service.encode;

public enum Encoders {
    PLAIN("PLAIN"),
    BASE64("BASE64"),
    HEX("HEX"),
    URL("URL"),
    ASCII("ASCII"),
    ;

    private final String hash;

    private Encoders(final String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return hash;
    }
}
