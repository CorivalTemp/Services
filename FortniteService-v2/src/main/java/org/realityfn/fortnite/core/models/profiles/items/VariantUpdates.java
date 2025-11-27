package org.realityfn.fortnite.core.models.profiles.items;

import java.util.List;

public class VariantUpdates {
    private String channel;
    private String active;
    private List<String> owned;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public List<String> getOwned() {
        return owned;
    }

    public void setOwned(List<String> owned) {
        this.owned = owned;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
