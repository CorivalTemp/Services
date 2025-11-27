package org.realityfn.fortnite.core.models.fortnite.matchmakingservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.realityfn.fortnite.core.utils.RandomStringGenerator;

import java.util.HashMap;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatchmakingTicketPayload {
    private String playerId;
    private List<String> partyPlayerIds;
    private String bucketId;
    private HashMap<String, String> attributes;
    private String expiresAt;
    private String nonce;


    public MatchmakingTicketPayload(String playerId, List<String> partyPlayerIds, String bucketId, HashMap<String, String> attributes, String expiresAt) {
        this.playerId = playerId;
        this.partyPlayerIds = partyPlayerIds;
        this.bucketId = bucketId;
        this.attributes = attributes;
        this.expiresAt = expiresAt;
        this.nonce = RandomStringGenerator.generate(32);
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public List<String> getPartyPlayerIds() {
        return partyPlayerIds;
    }

    public void setPartyPlayerIds(List<String> partyPlayerIds) {
        this.partyPlayerIds = partyPlayerIds;
    }

    public String getBucketId() {
        return bucketId;
    }

    public void setBucketId(String bucketId) {
        this.bucketId = bucketId;
    }

    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<String, String> attributes) {
        this.attributes = attributes;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    @Override
    public String toString() {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper()
                    .writeValueAsString(this);
        } catch (Exception e) {
            return "{}";
        }
    }
}
