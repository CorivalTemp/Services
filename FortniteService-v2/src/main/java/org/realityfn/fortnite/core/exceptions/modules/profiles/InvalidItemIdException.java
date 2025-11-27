package org.realityfn.fortnite.core.exceptions.modules.profiles;

import org.realityfn.common.exceptions.BaseException;

public class InvalidItemIdException extends BaseException {
    public InvalidItemIdException(String lockerItem) {
        super("errors.com.epicgames.fortnite.item_not_found", "Locker item " + lockerItem + " not found", 404, 16027);
        MessageVars = new String[]{lockerItem};
    }
}
