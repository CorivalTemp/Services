package org.realityfn.models.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.realityfn.models.account.auth.CreatedData;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "DeviceAuth")
public class DeviceAuth {
    @JsonProperty("deviceId")
    public String deviceId;

    @JsonProperty("accountId")
    public String accountId;

    @JsonProperty("secret")
    public String secret;

    @JsonProperty("userAgent")
    public String userAgent;

    @JsonProperty("created")
    public CreatedData created;

    @JsonProperty("lastAccess")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public CreatedData lastAccess;
}
