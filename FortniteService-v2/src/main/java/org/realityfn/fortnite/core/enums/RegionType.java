package org.realityfn.fortnite.core.enums;

public enum RegionType {
    //NAE("NAE", "wss://fortnite-matchmaking-public-service-live-oce.ol.epicgames.com:443"),
    //NAC("NAC", "wss://fortnite-matchmaking-public-service-live-nac.ol.epicgames.com:443"),
    //NAW("NAW", "wss://fortnite-matchmaking-public-service-live-naw.ol.epicgames.com:443"),
    //OCE("OCE", "wss://fortnite-matchmaking-public-service-live-oce.ol.epicgames.com:443"),
    TEST("TEST", "wss://fortnite-matchmaking-public-service-live-testing.realityfn.org:443");
    //ASIA("ASIA", "wss://fortnite-matchmaking-public-service-live-asia.ol.epicgames.com:443"),
    //ME("ME", "wss://fortnite-matchmaking-public-service-live-me.ol.epicgames.com:443"),
    //EU("EU", "wss://fortnite-matchmaking-public-service-live-eu.ol.epicgames.com:443"),
    //BR("BR", "wss://fortnite-matchmaking-public-service-live-br.ol.epicgames.com:443");

    private final String regionId;
    private final String regionHost;

    RegionType(String regionId, String regionHost) {
        this.regionId = regionId;
        this.regionHost = regionHost;
    }

    public String getRegionId() {
        return regionId;
    }

    public String getRegionHost() {
        return regionHost;
    }

    public static RegionType fromRegionId(String regionId) {
        for (RegionType region : values()) {
            if (region.regionId.equals(regionId)) {
                return region;
            }
        }
        return null;
    }
}
