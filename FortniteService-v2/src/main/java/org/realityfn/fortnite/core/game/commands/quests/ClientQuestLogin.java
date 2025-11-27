package org.realityfn.fortnite.core.game.commands.quests;

import org.realityfn.fortnite.core.annotations.ProfileOperation;
import org.realityfn.fortnite.core.enums.ProfileRoute;
import org.realityfn.fortnite.core.game.commands.OperationContext;
import org.realityfn.fortnite.core.game.commands.ProfileCommand;
import org.realityfn.fortnite.core.models.profiles.Profile;

import java.time.Instant;

@ProfileOperation(operation = "ClientQuestLogin", profiles = {"athena", "campaign"}, routes = {ProfileRoute.CLIENT})
public class ClientQuestLogin extends ProfileCommand {

    @Override
    public void invokeProfileCommand(OperationContext operationContext) {
        Profile profile = operationContext.getPrimaryProfile();
        profile.changeStat("last_xp_interaction", Instant.now().toString());
    }
}