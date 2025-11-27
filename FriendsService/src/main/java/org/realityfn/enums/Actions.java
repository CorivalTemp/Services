package org.realityfn.enums;

import java.util.ArrayList;
import java.util.List;

public class Actions {
    public static final int NONE = 0;
    public static final int CREATE = 1;
    public static final int READ = 2;
    public static final int UPDATE = 4;
    public static final int DELETE = 8;
    public static final int ALL = 15;
    public static final int DENY = 16;

    private Actions() {}
    public static String grabActionName(int action) {
        if (action == 0) {
            return "NONE";
        }
        if (action == 15) {
            return "ALL";
        }

        String[] actionNames = {"CREATE", "READ", "UPDATE", "DELETE", "DENY"};
        List<String> selectedActions = new ArrayList<>();

        for (int i = 0; i < actionNames.length; i++) {
            if ((action & (1 << i)) != 0) {
                selectedActions.add(actionNames[i]);
            }
        }

        return String.join(" ", selectedActions);
    }

}