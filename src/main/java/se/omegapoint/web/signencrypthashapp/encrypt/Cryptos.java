package se.omegapoint.web.signencrypthashapp.encrypt;

public enum Cryptos {
    AES("AES"),
    DES("DES"),
    DESede("DESede"),
    BLOWFISH("Blowfish"),
    ;

    private final String crypto;

    private Cryptos(final String crypto) {
        this.crypto = crypto;
    }

    @Override
    public String toString() {
        return crypto;
    }
}
