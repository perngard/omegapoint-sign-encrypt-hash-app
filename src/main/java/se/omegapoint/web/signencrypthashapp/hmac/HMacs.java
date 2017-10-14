package se.omegapoint.web.signencrypthashapp.hmac;

public enum HMacs {
    HMACSHA1("HmacSHA1"),
    HMACSHA256("HmacSHA256"),
    ;

    private final String hash;

    private HMacs(final String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return hash;
    }
}
