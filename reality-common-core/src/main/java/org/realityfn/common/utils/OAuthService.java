package org.realityfn.common.utils;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;import jakarta.ws.rs.core.UriInfo;
import org.realityfn.common.enums.Actions;
import org.realityfn.common.exceptions.common.oauth.InvalidOAuthTokenException;
import org.realityfn.common.managers.JwtGenerator;
import org.realityfn.common.models.OAuthToken;
import org.realityfn.common.models.Permission;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.zip.Inflater;

public class OAuthService {

    private static String decompressString(String base64String) {
        try {
            byte[] compressedData = Base64.getDecoder().decode(base64String);

            Inflater inflator = new Inflater();
            inflator.setInput(compressedData);

            byte[] buffer = new byte[1024];
            int decompressedDataLength;
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                while (!inflator.finished()) {
                    decompressedDataLength = inflator.inflate(buffer);
                    outputStream.write(buffer, 0, decompressedDataLength);
                }

                return outputStream.toString("UTF-8");
            }
        } catch (Exception e) {
            System.out.println("Error decompressing string: ");

            e.printStackTrace();
            return null;
        }
    }

    public static OAuthToken validateToken(String token, List<String> requiredResources, List<Integer> requiredActions, UriInfo uriInfo) throws RuntimeException {
        if (token.startsWith("eg1~")) {
            return validateJwtToken(token.replace("eg1~", ""), requiredResources, requiredActions, uriInfo);
        }
        return validateStoredToken(token, requiredResources, requiredActions, uriInfo);
    }

    private static OAuthToken validateJwtToken(String token, List<String> requiredResource, List<Integer> requiredAction, UriInfo uriInfo) throws RuntimeException {
        DecodedJWT decodedToken = JwtGenerator.getInstance().decodeJwt(token);

        if (!"s".equals(decodedToken.getClaim("t").asString())) {
            return null;
        }

        OAuthToken oAuthToken = mapJwtToOAuthToken(decodedToken);
        if (Instant.parse(oAuthToken.expires_at).isBefore(Instant.now())) {
            System.out.println("Token expired at " + oAuthToken.expires_at);
            return null;
        }

        PermissionCheckerImpl checker = new PermissionCheckerImpl(oAuthToken, uriInfo);
        checker.validatePermissions(requiredResource, requiredAction); // throws if missing
        return oAuthToken;
    }

    private static OAuthToken validateStoredToken(String token, List<String> permissions, List<Integer> actions, UriInfo uriInfo) {
        if (token == null || token.isBlank()) {
            System.out.println("method");
            return null;
        }
        try {
            //System.out.println("method 2");
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://account-public-service-prod.realityfn.org/account/api/oauth/verify?includePerms=1"))
                    .header("Authorization", "Bearer " + token)
                    .header("Accept", "application/json")
                    .timeout(Duration.ofSeconds(30))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                return null;
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            try {
                JsonNode rootNode = mapper.readTree(response.body());
                OAuthToken verifiedToken = mapper.treeToValue(rootNode, OAuthToken.class);


                // Use PermissionCheckerImpl to validate permissions
                PermissionCheckerImpl checker = new PermissionCheckerImpl(verifiedToken, uriInfo);
                checker.validatePermissions(permissions, actions);
                return verifiedToken;

            } catch (JsonProcessingException e) {
                System.out.println(e.getLocation().toString());
                throw new JsonMappingException("Unreal");
            }

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static OAuthToken mapJwtToOAuthToken(DecodedJWT decodedToken) {
        try {
            OAuthToken oAuthToken = new OAuthToken();
            if (!"s".equals(decodedToken.getClaim("t").asString())) {
                throw new InvalidOAuthTokenException("Token is not a valid session token.");
            }
            if (!(decodedToken.getClaim("p").isMissing() || decodedToken.getClaim("p").asString() == null)) {
                oAuthToken.perms = Arrays.stream(Objects.requireNonNull(decompressString(decodedToken.getClaim("p").asString())).split(","))
                        .map(resourceAction -> {
                            String[] parts = resourceAction.split("=");
                            return new Permission(parts[0], (Integer.parseInt(parts[1])));
                        })
                        .toList();
            }
            else {
                oAuthToken.perms = List.of();
            }

            oAuthToken.access_token = decodedToken.getClaim("jti").asString();
            oAuthToken.expires_at = String.valueOf(Instant.ofEpochSecond(decodedToken.getClaim("exp").asLong()));
            oAuthToken.auth_method = decodedToken.getClaim("am").asString();
            oAuthToken.client_id = decodedToken.getClaim("clid").asString();
            oAuthToken.internal_client = Boolean.parseBoolean(decodedToken.getClaim("ic").asString());
            oAuthToken.client_service = decodedToken.getClaim("clsvc").asString();
            oAuthToken.app = decodedToken.getClaim("app").asString();
            oAuthToken.session_id = decodedToken.getClaim("jti").asString();
            oAuthToken.token_type = "bearer";

            if (!"client_credentials".equals(oAuthToken.auth_method)) {
                oAuthToken.account_id = decodedToken.getClaim("iai").asString();
                oAuthToken.in_app_id = decodedToken.getClaim("sub").asString();
                oAuthToken.displayName = decodedToken.getClaim("dn").asString();
            }

            return oAuthToken;
        }
        catch (Exception e) {
            throw new InvalidOAuthTokenException("There was an issue verifying token. Token contains invalid data.");
        }
    }
}