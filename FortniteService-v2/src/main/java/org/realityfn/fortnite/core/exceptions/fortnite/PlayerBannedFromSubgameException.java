package org.realityfn.fortnite.core.exceptions.fortnite;

import org.realityfn.common.exceptions.BaseException;
import org.realityfn.fortnite.core.exceptions.MatchmakingBaseException;

public class PlayerBannedFromSubgameException extends MatchmakingBaseException {
    public PlayerBannedFromSubgameException(long banDaysRemaining) {
        super(
                "errors.com.epicgames.fortnite.player_banned_from_sub_game",
                "Banned from matchmaking",
                403,
                16136,
                banDaysRemaining
        );
    }
}
