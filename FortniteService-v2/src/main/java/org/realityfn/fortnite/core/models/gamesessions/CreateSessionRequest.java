package org.realityfn.fortnite.core.models.gamesessions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import net.sf.oval.constraint.NotNull;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Deprecated(since = "right now")
public class CreateSessionRequest {

    @NotNull
    private String ownerId;

    @NotNull
    private String ownerName;

    @NotNull
    private String serverName;
    private String serverAddress;
    private Integer serverPort;

    @NotNull
    private Integer maxPublicPlayers;

    @NotNull
    private Integer maxPrivatePlayers;

    @NotNull
    private Integer openPublicPlayers;

    @NotNull
    private Integer openPrivatePlayers;
    private Boolean shouldAdvertise;
    private Boolean allowJoinInProgress;
    private Boolean isDedicated;
    private Boolean usesStats;
    private Boolean allowInvites;
    private Boolean usesPresence;
    private Boolean allowJoinViaPresence;
    private Boolean allowJoinViaPresenceFriendsOnly;

    @NotNull
    private String buildUniqueId;
    private Map<String, Object> attributes = new HashMap<>();
    private Integer sortWeight;

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public Integer getMaxPublicPlayers() {
        return maxPublicPlayers;
    }

    public void setMaxPublicPlayers(Integer maxPublicPlayers) {
        this.maxPublicPlayers = maxPublicPlayers;
    }

    public Integer getMaxPrivatePlayers() {
        return maxPrivatePlayers;
    }

    public void setMaxPrivatePlayers(Integer maxPrivatePlayers) {
        this.maxPrivatePlayers = maxPrivatePlayers;
    }

    public Integer getOpenPublicPlayers() {
        return openPublicPlayers;
    }

    public void setOpenPublicPlayers(Integer openPublicPlayers) {
        this.openPublicPlayers = openPublicPlayers;
    }

    public Integer getOpenPrivatePlayers() {
        return openPrivatePlayers;
    }

    public void setOpenPrivatePlayers(Integer openPrivatePlayers) {
        this.openPrivatePlayers = openPrivatePlayers;
    }

    public Boolean getShouldAdvertise() {
        return shouldAdvertise;
    }

    public void setShouldAdvertise(Boolean shouldAdvertise) {
        this.shouldAdvertise = shouldAdvertise;
    }

    public Boolean getAllowJoinInProgress() {
        return allowJoinInProgress;
    }

    public void setAllowJoinInProgress(Boolean allowJoinInProgress) {
        this.allowJoinInProgress = allowJoinInProgress;
    }

    public Boolean getDedicated() {
        return isDedicated;
    }

    public void setDedicated(Boolean dedicated) {
        isDedicated = dedicated;
    }

    public Boolean getUsesStats() {
        return usesStats;
    }

    public void setUsesStats(Boolean usesStats) {
        this.usesStats = usesStats;
    }

    public Boolean getAllowInvites() {
        return allowInvites;
    }

    public void setAllowInvites(Boolean allowInvites) {
        this.allowInvites = allowInvites;
    }

    public Boolean getUsesPresence() {
        return usesPresence;
    }

    public void setUsesPresence(Boolean usesPresence) {
        this.usesPresence = usesPresence;
    }

    public Boolean getAllowJoinViaPresence() {
        return allowJoinViaPresence;
    }

    public void setAllowJoinViaPresence(Boolean allowJoinViaPresence) {
        this.allowJoinViaPresence = allowJoinViaPresence;
    }

    public Boolean getAllowJoinViaPresenceFriendsOnly() {
        return allowJoinViaPresenceFriendsOnly;
    }

    public void setAllowJoinViaPresenceFriendsOnly(Boolean allowJoinViaPresenceFriendsOnly) {
        this.allowJoinViaPresenceFriendsOnly = allowJoinViaPresenceFriendsOnly;
    }

    public String getBuildUniqueId() {
        return buildUniqueId;
    }

    public void setBuildUniqueId(String buildUniqueId) {
        this.buildUniqueId = buildUniqueId;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Integer getSortWeight() {
        return sortWeight;
    }

    public void setSortWeight(Integer sortWeight) {
        this.sortWeight = sortWeight;
    }
}