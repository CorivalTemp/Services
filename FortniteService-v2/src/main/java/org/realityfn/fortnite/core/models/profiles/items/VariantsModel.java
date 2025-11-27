package org.realityfn.fortnite.core.models.profiles.items;

public class VariantsModel {
    private String channel;
    private String variant;

    // Constructors (optional, but often useful)
    public VariantsModel() {
    }

    public VariantsModel(String channel, String variant) {
        this.channel = channel;
        this.variant = variant;
    }

    // Getter for channel
    public String getChannel() {
        return channel;
    }

    // Setter for channel
    public void setChannel(String channel) {
        this.channel = channel;
    }

    // Getter for variant
    public String getVariant() {
        return variant;
    }

    // Setter for variant
    public void setVariant(String variant) {
        this.variant = variant;
    }
}
