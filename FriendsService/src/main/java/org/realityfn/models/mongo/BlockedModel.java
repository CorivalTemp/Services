package org.realityfn.models.mongo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public class BlockedModel {

    @JsonProperty("accountId")
    private String accountId;

    @JsonProperty("created")
    private String created;

    public BlockedModel() {}

    public BlockedModel(String accountId, String created) {
        this.accountId = accountId;
        this.created = created;
    }

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public String getCreated() { return created; }
    public void setCreated(String created) { this.created = created; }
}