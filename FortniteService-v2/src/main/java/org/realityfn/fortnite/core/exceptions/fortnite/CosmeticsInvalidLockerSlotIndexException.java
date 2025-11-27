package org.realityfn.fortnite.core.exceptions.fortnite;

import org.realityfn.common.exceptions.BaseException;

import java.util.Arrays;

public class CosmeticsInvalidLockerSlotIndexException extends BaseException {
    public CosmeticsInvalidLockerSlotIndexException(Object... args) {
        super(
                "errors.com.epicgames.fortnite.cosmetics_invalid_locker_slot_index",
                format("Invalid loadout index {}, slot is empty", args),
                400,
                16174
        );
        MessageVars = Arrays.stream(args)
                .map(String::valueOf)
                .toArray(String[]::new);
    }
}