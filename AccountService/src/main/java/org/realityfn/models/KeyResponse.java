package org.realityfn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KeyResponse {
    @JsonProperty("kty")
    private String kty;

    @JsonProperty("e")
    private String e;

    @JsonProperty("kid")
    private String kid;

    @JsonProperty("n")
    private String n;

    // Constructors
    public KeyResponse() {}
    public KeyResponse(RSAPublicKey key, String id) {
        this.kty = key.getAlgorithm();
        this.e = base64UrlEncode(key.getPublicExponent().toByteArray());
        this.kid = id;
        this.n = base64UrlEncode(key.getModulus().toByteArray());
    }

    // Getters and Setters
    public String getKty() { return kty; }
    public void setKty(String kty) { this.kty = kty; }

    public String getE() { return e; }
    public void setE(String e) { this.e = e; }

    public String getKid() { return kid; }
    public void setKid(String kid) { this.kid = kid; }

    public String getN() { return n; }
    public void setN(String n) { this.n = n; }

    // Utility Functions
    private String base64UrlEncode(byte[] data) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(data);
    }
}
