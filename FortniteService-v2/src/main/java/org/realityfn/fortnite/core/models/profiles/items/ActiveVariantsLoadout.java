package org.realityfn.fortnite.core.models.profiles.items;

import java.util.List;

public class ActiveVariantsLoadout {
    private List<Variant> variants;

    public ActiveVariantsLoadout() {
    }

    public ActiveVariantsLoadout(List<Variant> variants) {
        this.variants = variants;
    }

    public List<Variant> getVariants() {
        return variants;
    }

    public void setVariants(List<Variant> variants) {
        this.variants = variants;
    }

    public static class Variant {
        private String channel;
        private String active;

        public Variant() {
        }

        public Variant(String channel, String active) {
            this.channel = channel;
            this.active = active;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getActive() {
            return active;
        }

        public void setActive(String active) {
            this.active = active;
        }
    }
}