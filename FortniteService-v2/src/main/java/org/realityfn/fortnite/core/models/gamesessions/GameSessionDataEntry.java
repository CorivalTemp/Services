package org.realityfn.fortnite.core.models.gamesessions;

import com.fasterxml.jackson.annotation.JsonAlias;
import net.sf.oval.constraint.NotNull;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GameSessionDataEntry {

    @BsonProperty("_id")
    @JsonAlias("_id")
    private String id;

    private String ownerId;

    @NotNull
    private String ownerName;

    @NotNull
    private String serverName;

    @NotNull
    private String serverAddress;

    @NotNull
    private Integer serverPort;

    @NotNull
    private Integer maxPublicPlayers;

    @NotNull
    private Integer openPublicPlayers;

    @NotNull
    private Integer maxPrivatePlayers;

    @NotNull
    private Integer openPrivatePlayers;

    @NotNull
    private Map<String, Object> attributes;

    private List<String> publicPlayers = List.of();

    private List<String> privatePlayers = List.of();

    private Integer totalPlayers = 0;

    @NotNull
    private Boolean allowJoinInProgress;

    @NotNull
    private Boolean shouldAdvertise;

    @NotNull
    private Boolean isDedicated;

    @NotNull
    private Boolean usesStats;

    @NotNull
    private Boolean allowInvites;

    @NotNull
    private Boolean usesPresence;

    @NotNull
    private Boolean allowJoinViaPresence;

    @NotNull
    private Boolean allowJoinViaPresenceFriendsOnly;

    @NotNull
    private String buildUniqueId;

    @NotNull
    private String lastUpdated;

    @NotNull
    private Boolean started;

    public GameSessionDataEntry() {

    }

    public GameSessionDataEntry(String ownerName, String serverName, String serverAddress, Integer serverPort, Integer maxPublicPlayers, Integer openPublicPlayers, Integer maxPrivatePlayers, Integer openPrivatePlayers, Map<String, Object> attributes, List<String> publicPlayers, List<String> privatePlayers, Integer totalPlayers, Boolean allowJoinInProgress, Boolean shouldAdvertise, Boolean isDedicated, Boolean usesStats, Boolean allowInvites, Boolean usesPresence, Boolean allowJoinViaPresence, Boolean allowJoinViaPresenceFriendsOnly, String buildUniqueId, String lastUpdated, Boolean started) {
        id = UUID.randomUUID().toString().replace("-", "");
        ownerId = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        this.ownerName = ownerName;
        this.serverName = serverName;
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.maxPublicPlayers = maxPublicPlayers;
        this.openPublicPlayers = openPublicPlayers;
        this.maxPrivatePlayers = maxPrivatePlayers;
        this.openPrivatePlayers = openPrivatePlayers;
        this.attributes = attributes;
        this.publicPlayers = publicPlayers;
        this.privatePlayers = privatePlayers;
        this.totalPlayers = totalPlayers;
        this.allowJoinInProgress = allowJoinInProgress;
        this.shouldAdvertise = shouldAdvertise;
        this.isDedicated = isDedicated;
        this.usesStats = usesStats;
        this.allowInvites = allowInvites;
        this.usesPresence = usesPresence;
        this.allowJoinViaPresence = allowJoinViaPresence;
        this.allowJoinViaPresenceFriendsOnly = allowJoinViaPresenceFriendsOnly;
        this.buildUniqueId = buildUniqueId;
        this.lastUpdated = lastUpdated;
        this.started = started;
    }

    public GameSessionDataEntry(String id, String ownerId, String ownerName, String serverName, String serverAddress, Integer serverPort, Integer maxPublicPlayers, Integer openPublicPlayers, Integer maxPrivatePlayers, Integer openPrivatePlayers, Map<String, Object> attributes, List<String> publicPlayers, List<String> privatePlayers, Integer totalPlayers, Boolean allowJoinInProgress, Boolean shouldAdvertise, Boolean isDedicated, Boolean usesStats, Boolean allowInvites, Boolean usesPresence, Boolean allowJoinViaPresence, Boolean allowJoinViaPresenceFriendsOnly, String buildUniqueId, String lastUpdated, Boolean started) {
        this.id = id;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.serverName = serverName;
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.maxPublicPlayers = maxPublicPlayers;
        this.openPublicPlayers = openPublicPlayers;
        this.maxPrivatePlayers = maxPrivatePlayers;
        this.openPrivatePlayers = openPrivatePlayers;
        this.attributes = attributes;
        this.publicPlayers = publicPlayers;
        this.privatePlayers = privatePlayers;
        this.totalPlayers = totalPlayers;
        this.allowJoinInProgress = allowJoinInProgress;
        this.shouldAdvertise = shouldAdvertise;
        this.isDedicated = isDedicated;
        this.usesStats = usesStats;
        this.allowInvites = allowInvites;
        this.usesPresence = usesPresence;
        this.allowJoinViaPresence = allowJoinViaPresence;
        this.allowJoinViaPresenceFriendsOnly = allowJoinViaPresenceFriendsOnly;
        this.buildUniqueId = buildUniqueId;
        this.lastUpdated = lastUpdated;
        this.started = started;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getDedicated() {
        return isDedicated;
    }

    public void setDedicated(Boolean isDedicated) {
        this.isDedicated = isDedicated;
    }

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

    public Integer getOpenPublicPlayers() {
        return openPublicPlayers;
    }

    public void setOpenPublicPlayers(Integer openPublicPlayers) {
        this.openPublicPlayers = openPublicPlayers;
    }

    public Integer getMaxPrivatePlayers() {
        return maxPrivatePlayers;
    }

    public void setMaxPrivatePlayers(Integer maxPrivatePlayers) {
        this.maxPrivatePlayers = maxPrivatePlayers;
    }

    public Integer getOpenPrivatePlayers() {
        return openPrivatePlayers;
    }

    public void setOpenPrivatePlayers(Integer openPrivatePlayers) {
        this.openPrivatePlayers = openPrivatePlayers;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public List<String> getPublicPlayers() {
        return publicPlayers;
    }

    public void setPublicPlayers(List<String> publicPlayers) {
        this.publicPlayers = publicPlayers;
    }

    public List<String> getPrivatePlayers() {
        return privatePlayers;
    }

    public void setPrivatePlayers(List<String> privatePlayers) {
        this.privatePlayers = privatePlayers;
    }

    public Integer getTotalPlayers() {
        return totalPlayers;
    }

    public void setTotalPlayers(Integer totalPlayers) {
        this.totalPlayers = totalPlayers;
    }

    public Boolean getAllowJoinInProgress() {
        return allowJoinInProgress;
    }

    public void setAllowJoinInProgress(Boolean allowJoinInProgress) {
        this.allowJoinInProgress = allowJoinInProgress;
    }

    public Boolean getShouldAdvertise() {
        return shouldAdvertise;
    }

    public void setShouldAdvertise(Boolean shouldAdvertise) {
        this.shouldAdvertise = shouldAdvertise;
    }

    public Boolean getIsDedicated() {
        return isDedicated;
    }

    public void setIsDedicated(Boolean isDedicated) {
        this.isDedicated = isDedicated;
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

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Boolean getStarted() {
        return started;
    }

    public void setStarted(Boolean started) {
        this.started = started;
    }

    // Helpers fr
    public String fetchSessionKey() {
        return this.attributes.get("SESSIONKEY_s").toString();
    }

    public void clearPlayerArrays() {
        this.privatePlayers.clear();
        this.publicPlayers.clear();
    }
}