package org.realityfn.common.managers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.RSAKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.realityfn.common.exceptions.common.oauth.InvalidOAuthTokenException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.PublicKey;
import java.security.Security;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;

public class JwtGenerator {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private static JwtGenerator INSTANCE = null;

    public static synchronized JwtGenerator getInstance() {
        if (INSTANCE == null) {
            try {
                INSTANCE = new JwtGenerator();
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize JwtGenerator: " + e.getMessage());
            }
        }
        return INSTANCE;
    }

    public PublicKey loadPublicKey(String thumbprint) {
        try {
            // We request account service for the publickey from the thumbprint fetched in decodeJwt
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://account-public-service-prod.realityfn.org/account/api/publickeys/" + thumbprint))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 404) {
                throw new InvalidOAuthTokenException("Unknown RSA key. Thumbprint: " + thumbprint, new String[]{thumbprint});
            }
            String jwkJson = response.body();

            // Parse the JWK and convert it to PublicKey class
            RSAKey rsaKey = RSAKey.parse(jwkJson);
            return rsaKey.toRSAPublicKey();
        } catch (ParseException | JOSEException | InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    public DecodedJWT decodeJwt(String token) {
        DecodedJWT decodedToken = JWT.decode(token);

        String keyId = decodedToken.getKeyId();
        if (keyId == null || keyId.isEmpty()) {
            throw new InvalidOAuthTokenException("Token is missing key ID value");
        }
        PublicKey PUBLIC_KEY = loadPublicKey(keyId);

        return JWT.require(com.auth0.jwt.algorithms.Algorithm.RSA256((RSAPublicKey) PUBLIC_KEY, null))
                .build()
                .verify(token);
    }
}
