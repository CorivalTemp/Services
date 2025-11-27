package org.realityfn.fortnite.core.game.commands.athena;

import net.sf.oval.constraint.NotNull;
import org.realityfn.fortnite.core.annotations.ProfileOperation;
import org.realityfn.fortnite.core.enums.ProfileRoute;
import org.realityfn.fortnite.core.game.commands.OperationContext;
import org.realityfn.fortnite.core.game.commands.ProfileCommand;
import org.realityfn.fortnite.core.models.profiles.Profile;

import java.util.List;

@ProfileOperation(operation = "AthenaRemoveQuests", profiles = {"athena"}, routes = ProfileRoute.CLIENT)
public class AthenaRemoveQuests extends ProfileCommand {

    @NotNull
    List<String> removedQuests;

    @Override
    public void invokeProfileCommand(OperationContext operationContext) {
        Profile profile = operationContext.getPrimaryProfile();
        for (String itemId : removedQuests) {
            profile.removeItem(itemId);
        }
    }
}

