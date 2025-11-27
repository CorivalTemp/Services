package org.realityfn.fortnite.core.models.profiles.attributes;

public class Slot {
    public String[] items;
    public Object[] activeVariants;

    public Slot() {}
    public Slot(String[] items, Object[] activeVariants) {
        this.items = items;
        this.activeVariants = activeVariants;
    }
}

