package org.realityfn.models.authorization;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.realityfn.models.account.auth.PermissionTypeModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "clients")
public class Client {
    @Id
    public String id;

    @BsonProperty("clientId")
    public String clientId;

    @BsonProperty("clientSecret")
    public String clientSecret;

    @BsonProperty("clientName")
    public String clientName;

    @BsonProperty("redirectUrl")
    public String redirectUrl;

    @BsonProperty("internal")
    public boolean internal;

    @BsonProperty("type")
    public String clientType;

    @BsonProperty("clientService")
    public String clientService;

    @BsonProperty("verified")
    public boolean verified;

    @BsonProperty("allowedScopes")
    public List<String> allowedScopes;

    @BsonProperty("grant_types")
    public List<String> grantTypes;

    @BsonProperty("Permissions")
    public PermissionTypeModel permissions;

    @BsonProperty("disabled")
    public boolean disabled;

    @BsonProperty("sessionLifetime")
    public int sessionLifetime;
}
