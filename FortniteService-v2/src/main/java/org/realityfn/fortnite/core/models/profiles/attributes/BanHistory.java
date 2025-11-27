package org.realityfn.fortnite.core.models.profiles.attributes;

import org.realityfn.fortnite.core.enums.PlayerBanReasons;

import java.util.Map;

public class BanHistory {
    public Map<PlayerBanReasons, Integer> banCount;
    public Map<String, String> banTier;
}
