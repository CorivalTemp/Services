package org.realityfn.models.authorization;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.realityfn.models.account.auth.PermissionTypeModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "oauth_tokens")
public class OAuthToken {
    @Id
    public String id;

    @BsonProperty("access_token")
    public String accessToken;

    @BsonProperty("refresh_token")
    public String refreshToken;

    @BsonProperty("expires_at")
    public Instant expiresAt;

    @BsonProperty("refresh_expires_at")
    public Instant refreshExpiresAt;

    @BsonProperty("token_type")
    public String tokenType;

    @BsonProperty("client_id")
    public String clientId;

    @BsonProperty("internal_client")
    public boolean internalClient;

    @BsonProperty("client_service")
    public String clientService;

    @BsonProperty("account_id")
    public String accountId;

    @BsonProperty("display_name")
    public String displayName;

    @BsonProperty("app")
    public String app;

    @BsonProperty("in_app_id")
    public String inAppId;

    @BsonProperty("product_id")
    public String productId;

    @BsonProperty("application_id")
    public String applicationId;

    @BsonProperty("session_id")
    public String sessionId;

    @BsonProperty("auth_method")
    public String authMethod;

    @BsonProperty("permissions")
    public List<PermissionMapping> permissions;
}