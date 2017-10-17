package se.omegapoint.web.signencrypthashapp.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import se.omegapoint.web.signencrypthashapp.common.TextType;

public class JWTTokenVO {

    private String algorithm;
    private String data;
    private String token;
    private String pubkey;
    private String privkey;
    private String secret;
    private String secretType = TextType.TEXT.toString();

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getSecretType() {
        return secretType;
    }

    public void setSecretType(String secretType) {
        this.secretType = secretType;
    }

    public String getPubkey() {
        return pubkey;
    }

    public void setPubkey(String pubkey) {
        this.pubkey = pubkey;
    }

    public String getPrivkey() {
        return privkey;
    }

    public void setPrivkey(String privkey) {
        this.privkey = privkey;
    }
}
