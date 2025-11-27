package org.realityfn.fortnite.core.enums;

public enum ProfileRoute {
    CLIENT("client"),
    PUBLIC("public"),
    DEDICATED_SERVER("dedicated_server"),
    CHEAT("developer_cheats"),
    ALL("all"); // Unsure if epic does this but just incase

    private final String value;

    ProfileRoute(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean contains(String input) {
        for (ProfileRoute route : ProfileRoute.values()) {
            if (route.getValue().equalsIgnoreCase(input)) {
                return true;
            }
        }
        return false;
    }
}
