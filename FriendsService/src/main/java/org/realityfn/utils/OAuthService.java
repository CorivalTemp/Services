package org.realityfn.utils;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.realityfn.enums.Actions;
import org.realityfn.errorhandling.exceptions.oauth.MissingTokenPermissionsException;
import org.realityfn.managers.JwtGenerator;
import org.realityfn.models.OAuthToken;
import org.realityfn.models.PermissionMapping;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.Inflater;

import static org.realityfn.enums.Actions.grabActionName;

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

                return outputStream.toString(StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            System.out.println("Error decompressing string: ");
            e.printStackTrace();
            return null;
        }
    }

    public static OAuthToken validateToken(String token, String requiredResource, int requiredAction) throws RuntimeException {
        if (token.startsWith("eg1~")) {
            return validateStoredToken(token.replace("eg1~", "eg1~"), requiredResource, requiredAction); // HACK: Please don't do this will be fixed soon:tm:
        }
        return validateStoredToken(token, requiredResource, requiredAction);
    }

    private OAuthToken validateJwtToken(String token, String requiredResource, int requiredAction) throws RuntimeException {
        DecodedJWT decodedToken = JwtGenerator.getInstance().decodeJwt(token);

        if (!"s".equals(decodedToken.getClaim("t").asString())) {
            return null;
        }

        OAuthToken oAuthToken = mapJwtToOAuthToken(decodedToken);
        if (oAuthToken.expiresAt.isBefore(Instant.now())) {
            return null;
        }

        return hasPermission(oAuthToken.permissions, requiredResource, requiredAction) ? oAuthToken : null;
    }


    private static OAuthToken validateStoredToken(String token, String permission, int action) {
        if (token == null || token.isBlank()) {
            return null;
        }
        try {
            @SuppressWarnings("")
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
                // Define token
                OAuthToken verifiedToken = mapper.treeToValue(rootNode, OAuthToken.class);

                // Extract permissions from verification response
                JsonNode permissionsNode = rootNode.get("perms");
                PermissionMapping[] permissions = mapper.treeToValue(permissionsNode, PermissionMapping[].class);

                // Check token permissions
                boolean hasPermission = Arrays.stream(permissions).anyMatch(x -> {
                    if (permission.isEmpty() && action == Actions.NONE) {
                        return true;
                    }
                    boolean actionMatches = (x.action & action) == action;
                    boolean resourceMatches = matchesResourcePattern(x.resource, permission);

                    if (resourceMatches) {
                        if (x.action == 15) {
                            actionMatches = true;
                        }
                        if ((x.action & 16) != 0) actionMatches = false;
                    }

                    return resourceMatches && actionMatches;
                });

                if (hasPermission) {
                    return verifiedToken;
                } else {
                    throw new MissingTokenPermissionsException(permission, action);
                }

            } catch (JsonProcessingException e) {
                return null;
            }

        } catch (InterruptedException | IOException e) {
            return null;
        }
    }

    private static boolean matchesResourcePattern(String permissionResource, String requestedResource) {
        // This part is optional, but saves some cpu cycles so might as well include it
        if (permissionResource.equals(requestedResource)) {
            return true;
        }

        String regexPattern = permissionResource.replace("*", ".*");
        regexPattern = "^" + regexPattern + "$";

        try {
            return requestedResource.matches(regexPattern);
        }
        catch (Exception e) {
            return false; // Should never happen
        }
    }

    private OAuthToken mapJwtToOAuthToken(DecodedJWT decodedToken) {
        List<PermissionMapping> permissions = Arrays.stream(decompressString(decodedToken.getClaim("p").asString()).split(","))
                .map(resourceAction -> {
                    String[] parts = resourceAction.split("=");
                    return new PermissionMapping(parts[0], (Integer.parseInt(parts[1])));
                })
                .collect(Collectors.toList());

        OAuthToken oAuthToken = new OAuthToken();
        oAuthToken.accessToken = decodedToken.getClaim("jti").asString();
        oAuthToken.expiresAt = Instant.ofEpochSecond(decodedToken.getClaim("exp").asLong());
        oAuthToken.authMethod = decodedToken.getClaim("am").asString();
        oAuthToken.clientId = decodedToken.getClaim("clid").asString();
        oAuthToken.internalClient = Boolean.parseBoolean(decodedToken.getClaim("ic").asString());
        oAuthToken.clientService = decodedToken.getClaim("clsvc").asString();
        oAuthToken.app = decodedToken.getClaim("app").asString();
        oAuthToken.sessionId = decodedToken.getClaim("jti").asString();
        oAuthToken.tokenType = "bearer";
        oAuthToken.permissions = permissions;

        if (!"client_credentials".equals(oAuthToken.authMethod)) {
            oAuthToken.accountId = decodedToken.getClaim("iai").asString();
            oAuthToken.inAppId = decodedToken.getClaim("sub").asString();
            oAuthToken.displayName = decodedToken.getClaim("dn").asString();
        }

        return oAuthToken;
    }

    private boolean hasPermission(List<PermissionMapping> permissions, String requiredResource, int requiredAction) {
        if (requiredResource == null || requiredAction == 0) {
            return true;
        }
        return permissions.stream().anyMatch(perm -> matchesResource(perm.resource, requiredResource)
                && ((perm.action & requiredAction) != 0 || perm.action == Actions.ALL)
                && perm.action != Actions.DENY);
    }

    private boolean matchesResource(String permResource, String requiredResource) {
        return permResource.equals(requiredResource) || (permResource.endsWith(":*") && requiredResource.startsWith(permResource.substring(0, permResource.length() - 2)));
    }
}