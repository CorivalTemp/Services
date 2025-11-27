package org.realityfn.fortnite.core.game.commands;

import org.realityfn.fortnite.core.managers.profiles.ProfileManager;
import org.realityfn.fortnite.core.models.profiles.Profile;
import org.realityfn.fortnite.core.models.profiles.notifications.Notification;

import java.util.*;

public class OperationContext {
    private final String accountId;
    private final Profile primaryProfile;
    private final Map<String, Profile> modifiedProfiles = new HashMap<>();
    private ArrayList<Notification> notifications;

    public OperationContext(String accountId, Profile primaryProfile) {
        this.accountId = accountId;
        this.primaryProfile = primaryProfile;
        this.modifiedProfiles.put(primaryProfile.getProfileId(), primaryProfile);
        this.notifications = new ArrayList<>();
    }

    public Profile getPrimaryProfile() {
        return primaryProfile;
    }

    public Profile getProfile(String profileId) {
        if (modifiedProfiles.containsKey(profileId)) {
            return modifiedProfiles.get(profileId);
        }

        Profile profile = ProfileManager.getInstance().getOrCreateProfile(accountId, profileId);
        modifiedProfiles.put(profileId, profile);
        return profile;
    }

    public Collection<Profile> getModifiedProfiles() {
        return modifiedProfiles.values();
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    public void addNotifications(Notification notification) {
        this.notifications.add(notification);
    }
}
