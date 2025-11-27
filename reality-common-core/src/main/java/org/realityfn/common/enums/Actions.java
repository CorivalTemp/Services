package org.realityfn.common.enums;

import java.util.ArrayList;
import java.util.List;

public enum Actions {
    NONE(0, "NONE"),
    CREATE(1, "CREATE"),
    READ(2, "READ"),
    UPDATE(4, "UPDATE"),
    DELETE(8, "DELETE"),
    ALL(15, "ALL"),
    DENY(16, "DENY");

    private final int value;
    private final String name;

    Actions(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static String grabActionName(int action) {
        if (action == NONE.value) {
            return NONE.name;
        }
        if (action == ALL.value) {
            return ALL.name;
        }

        List<String> names = new ArrayList<>();
        for (Actions a : new Actions[]{CREATE, READ, UPDATE, DELETE, DENY}) {
            if ((action & a.value) != 0) {
                names.add(a.name);
            }
        }

        return String.join(" ", names);
    }

    public static Actions fromValue(int value) {
        for (Actions action : values()) {
            if (action.value == value) {
                return action;
            }
        }
        throw new IllegalArgumentException("Unknown action value: " + value);
    }

    public static int combine(Actions... actions) {
        int combined = 0;
        for (Actions action : actions) {
            combined |= action.value;
        }
        return combined;
    }

    public static boolean hasAction(int permissions, Actions action) {
        return (permissions & action.value) != 0;
    }
}