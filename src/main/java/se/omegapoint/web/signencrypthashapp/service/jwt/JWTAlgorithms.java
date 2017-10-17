package se.omegapoint.web.signencrypthashapp.service.jwt;

public enum JWTAlgorithms {

    NONE("none"),
//TODO    RS256("SHA256withRSA"),
    HS256("HmacSHA256"),
    ;

    private final String signature;

    private JWTAlgorithms(final String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return signature;
    }
}
