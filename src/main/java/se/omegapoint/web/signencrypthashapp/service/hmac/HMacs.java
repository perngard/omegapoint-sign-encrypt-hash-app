package se.omegapoint.web.signencrypthashapp.service.hmac;

public enum HMacs {
    HMACMD5("HmacMD5"),
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
