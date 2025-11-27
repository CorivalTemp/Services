package org.realityfn.fortnite.core.models.profiles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.realityfn.fortnite.core.models.profiles.notifications.Notification;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileResponse
{
    private int profileRevision;

    private String profileId;

    private int profileChangesBaseRevision;

    private List<ProfileChangeRequest> profileChanges;

    private List<MultiProfileUpdateResponse> multiUpdate;

    private List<Notification> notifications;

    private String lockExpiration;

    private int profileCommandRevision;

    private String serverTime;

    private int responseVersion;

    public ProfileResponse(Profile profile, List<ProfileChangeRequest> profileChangeRequests)
    {
        profileRevision = profile.getRevision();
        profileId = profile.getProfileId();
        profileChangesBaseRevision = !profileChangeRequests.isEmpty() ? profile.getRevision() - 1 : profile.getRevision();
        profileChanges = profileChangeRequests;
        lockExpiration = profile.getProfileLockExpiration();
        profileCommandRevision = profile.getCommandRevision();
        serverTime = Instant.now().truncatedTo(ChronoUnit.MILLIS).toString();
        responseVersion = 1;
    }

    /**
     *  Constructor for Multi Profile Updates (check out: <a href="https://p4-swarm.epicgames.net/files/Fortnite/Main/FortniteGame/GameService/Modules/GameSubCatalog/epic-module-GameSubCatalog-Core/src/main/java/com/epicgames/modules/gamesubcatalog/core/commands/PurchaseMtxPurchase.java">...</a>
     */
    public ProfileResponse(Profile profile, List<ProfileChangeRequest> profileChangeRequests, List<Notification> notifications, List<MultiProfileUpdateResponse> multiUpdate)
    {
        profileRevision = profile.getRevision();
        profileId = profile.getProfileId();
        profileChangesBaseRevision = !profileChangeRequests.isEmpty() ? profile.getRevision() - 1 : profile.getRevision();
        profileChanges = profileChangeRequests;
        this.multiUpdate = multiUpdate;
        this.notifications = notifications;
        lockExpiration = profile.getProfileLockExpiration();
        profileCommandRevision = profile.getCommandRevision();
        serverTime = Instant.now().truncatedTo(ChronoUnit.MILLIS).toString();
        responseVersion = 1;
    }

    public int getProfileRevision()
    {
        return profileRevision;
    }

    public void setProfileRevision(int profileRevision)
    {
        this.profileRevision = profileRevision;
    }

    public int getProfileChangesBaseRevision()
    {
        return profileChangesBaseRevision;
    }

    public void setProfileChangesBaseRevision(int profileChangesBaseRevision)
    {
        this.profileChangesBaseRevision = profileChangesBaseRevision;
    }

    public String getProfileId()
    {
        return profileId;
    }

    public List<ProfileChangeRequest> getProfileChanges() {
        return profileChanges;
    }

    public void setProfileId(String profileId)
    {
        this.profileId = profileId;
    }

    public String getLockExpiration()
    {
        return lockExpiration;
    }

    public void setLockExpiration(String lockExpiration)
    {
        this.lockExpiration = lockExpiration;
    }

    public int getProfileCommandRevision()
    {
        return profileCommandRevision;
    }

    public void setProfileCommandRevision(int profileCommandRevision)
    {
        this.profileCommandRevision = profileCommandRevision;
    }

    public String getServerTime()
    {
        return serverTime;
    }

    public void setServerTime(String serverTime)
    {
        this.serverTime = serverTime;
    }

    public int getResponseVersion()
    {
        return responseVersion;
    }

    public void setResponseVersion(int responseVersion)
    {
        this.responseVersion = responseVersion;
    }


    public void addChangeRequest(ProfileChangeRequest changeRequest)
    {
        profileChanges.add(changeRequest);
    }

    public void resetChangeRequests()
    {
        if (!profileChanges.isEmpty()) profileChanges.clear();
    }

    public void resetMultiUpdateAndNotifications()
    {
        this.multiUpdate = null;
        this.notifications = null;
    }

    public List<MultiProfileUpdateResponse> getMultiUpdate() {
        return multiUpdate;
    }

    public void setMultiUpdate(List<MultiProfileUpdateResponse> multiUpdate) {
        this.multiUpdate = multiUpdate;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
