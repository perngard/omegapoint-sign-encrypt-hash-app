package se.omegapoint.web.signencrypthashapp.service.decode;

public enum Decoders {
    BASE64("BASE64"),
    HEX("HEX"),
    URL("URL"),
    ASCII("ASCII"),
    ;

    private final String hash;

    private Decoders(final String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return hash;
    }
}
