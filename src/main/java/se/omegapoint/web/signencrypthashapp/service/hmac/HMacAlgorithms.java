package se.omegapoint.web.signencrypthashapp.service.hmac;

public enum HMacAlgorithms {
    HMACMD5("HmacMD5"),
    HMACSHA1("HmacSHA1"),
    HMACSHA256("HmacSHA256"),
    ;

    private final String hash;

    private HMacAlgorithms(final String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return hash;
    }
}
