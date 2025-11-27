package org.realityfn.fortnite.core.game.commands.mtx;

import net.sf.oval.constraint.NotNull;
import org.realityfn.fortnite.core.annotations.ProfileOperation;
import org.realityfn.fortnite.core.enums.MtxPlatform;
import org.realityfn.fortnite.core.enums.ProfileRoute;
import org.realityfn.fortnite.core.game.commands.OperationContext;
import org.realityfn.fortnite.core.game.commands.ProfileCommand;
import org.realityfn.fortnite.core.models.profiles.Profile;

import java.time.Instant;

@ProfileOperation(operation = "SetMtxPlatform", profiles = "common_core", routes = ProfileRoute.CLIENT)
public class SetMtxPlatform extends ProfileCommand {

    @NotNull
    MtxPlatform newPlatform;

    @Override
    public void invokeProfileCommand(OperationContext operationContext) {
        Profile profile = operationContext.getPrimaryProfile();
        profile.changeStat("newPlatform", newPlatform);
    }
}

