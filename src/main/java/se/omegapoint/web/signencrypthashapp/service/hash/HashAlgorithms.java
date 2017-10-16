package se.omegapoint.web.signencrypthashapp.service.hash;

public enum HashAlgorithms {
    MD5("MD5"),
    SHA1("SHA-1"),
    SHA224("SHA-224"),
    SHA256("SHA-256"),
    SHA384("SHA-384"),
    SHA512("SHA-512"),
    ;

    private final String hash;

    private HashAlgorithms(final String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return hash;
    }
}
