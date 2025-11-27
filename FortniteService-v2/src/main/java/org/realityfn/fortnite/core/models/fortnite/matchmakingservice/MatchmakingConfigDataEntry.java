package org.realityfn.fortnite.core.models.fortnite.matchmakingservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.realityfn.fortnite.core.config.BuildProperties;
import org.realityfn.fortnite.core.models.lightswitch.LightswitchServiceStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatchmakingConfigDataEntry {
    @Id
    @JsonProperty("_id")
    private String id;

    private List<String> blacklistedBucketIds;

    private List<String> blacklistedPlaylistIds;

    @JsonProperty("isMatchmakingDisabled")
    private boolean isMatchmakingDisabled;

    public MatchmakingConfigDataEntry() {
        id = BuildProperties.APP;
        blacklistedBucketIds = List.of();
        blacklistedPlaylistIds = List.of();
        isMatchmakingDisabled = false;
    }

    public boolean isMatchmakingDisabled() {
        return this.isMatchmakingDisabled;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getBlacklistedBucketIds() {
        return blacklistedBucketIds;
    }

    public void setBlacklistedBucketIds(List<String> blacklistedBucketIds) {
        this.blacklistedBucketIds = blacklistedBucketIds;
    }

    public List<String> getBlacklistedPlaylistIds() {
        return blacklistedPlaylistIds;
    }

    public void setBlacklistedPlaylistIds(List<String> blacklistedPlaylistIds) {
        this.blacklistedPlaylistIds = blacklistedPlaylistIds;
    }

}
