package se.omegapoint.web.signencrypthashapp.service.signature;

public enum SignatureAlgorithms {
//TODO - all below
    SHA1DSA("SHA1withDSA"),
    MD2RSA("MD2withRSA"),
    MD5RSA("MD5withRSA"),
    SHA1RSA("SHA1withRSA"),
    SHA256RSA("SHA256withRSA"),
    ;

    private final String signature;

    private SignatureAlgorithms(final String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return signature;
    }
}
