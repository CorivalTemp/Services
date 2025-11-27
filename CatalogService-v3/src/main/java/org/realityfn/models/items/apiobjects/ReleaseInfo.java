package org.realityfn.models.items.apiobjects;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;

public class ReleaseInfo {
    @JsonProperty("id")
    private String id;

    @JsonProperty("appId")
    private String appId;

    @JsonProperty("compatibleApps")
    private List<String> compatibleApps;

    @JsonProperty("platform")
    private List<String> platform;

    @JsonProperty("dateAdded")
    private Instant dateAdded;

    @JsonProperty("releaseNote")
    private String releaseNote;

    @JsonProperty("versionTitle")
    private String versionTitle;

    public ReleaseInfo() {}

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getAppId() { return appId; }
    public void setAppId(String appId) { this.appId = appId; }

    public List<String> getCompatibleApps() { return compatibleApps; }
    public void setCompatibleApps(List<String> compatibleApps) { this.compatibleApps = compatibleApps; }

    public List<String> getPlatform() { return platform; }
    public void setPlatform(List<String> platform) { this.platform = platform; }

    public Instant getDateAdded() { return dateAdded; }
    public void setDateAdded(Instant dateAdded) { this.dateAdded = dateAdded; }

    public String getReleaseNote() { return releaseNote; }
    public void setReleaseNote(String releaseNote) { this.releaseNote = releaseNote; }

    public String getVersionTitle() { return versionTitle; }
    public void setVersionTitle(String versionTitle) { this.versionTitle = versionTitle; }
}