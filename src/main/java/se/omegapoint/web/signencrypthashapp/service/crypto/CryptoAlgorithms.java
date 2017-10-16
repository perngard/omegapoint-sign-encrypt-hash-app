package se.omegapoint.web.signencrypthashapp.service.crypto;

public enum CryptoAlgorithms {
    AES("AES"),
    DES("DES"),
    DESede("DESede"),
    BLOWFISH("Blowfish"),
    RSA("RSA"),
    ;

    private final String crypto;

    private CryptoAlgorithms(final String crypto) {
        this.crypto = crypto;
    }

    @Override
    public String toString() {
        return crypto;
    }
}
