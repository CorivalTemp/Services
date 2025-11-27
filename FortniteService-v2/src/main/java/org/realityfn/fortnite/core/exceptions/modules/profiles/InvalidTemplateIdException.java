package org.realityfn.fortnite.core.exceptions.modules.profiles;

import org.realityfn.common.exceptions.BaseException;

public class InvalidTemplateIdException extends BaseException {
    public InvalidTemplateIdException(String invalidTemplateId) {
        super("errors.com.epicgames.fortnite.id_invalid", String.format("Item %s not found to slot in cosmetic locker", invalidTemplateId), 400, 16027);
    }
}
