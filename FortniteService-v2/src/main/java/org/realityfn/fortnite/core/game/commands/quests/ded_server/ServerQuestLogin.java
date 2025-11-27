package org.realityfn.fortnite.core.game.commands.quests.ded_server;

import net.sf.oval.constraint.NotNull;
import org.realityfn.fortnite.core.annotations.ProfileOperation;
import org.realityfn.fortnite.core.enums.ProfileRoute;
import org.realityfn.fortnite.core.game.commands.OperationContext;
import org.realityfn.fortnite.core.game.commands.ProfileCommand;
import org.realityfn.fortnite.core.models.profiles.Profile;

import java.time.Instant;

@ProfileOperation(operation = "ServerQuestLogin", profiles = {"athena", "campaign"}, routes = ProfileRoute.DEDICATED_SERVER, bypassesProfileLock = true)
public class ServerQuestLogin extends ProfileCommand {

    @NotNull
    private String matchmakingSessionId;

    @Override
    public void invokeProfileCommand(OperationContext operationContext) {
        Profile profile = operationContext.getPrimaryProfile();
        profile.changeStat("last_xp_interaction", Instant.now().toString());
    }
}

