package org.realityfn.fortnite.core.game.commands.athena;

import net.sf.oval.constraint.NotNull;
import org.realityfn.fortnite.core.annotations.ProfileOperation;
import org.realityfn.fortnite.core.enums.ProfileRoute;
import org.realityfn.fortnite.core.game.commands.OperationContext;
import org.realityfn.fortnite.core.game.commands.ProfileCommand;
import org.realityfn.fortnite.core.models.profiles.Profile;

import java.util.List;

@ProfileOperation(operation = "MarkItemSeen", profiles = {"athena", "campaign"}, routes = ProfileRoute.CLIENT)
public class MarkItemSeen extends ProfileCommand {

    @NotNull
    List<String> itemIds;

    @Override
    public void invokeProfileCommand(OperationContext operationContext) {
        Profile profile = operationContext.getPrimaryProfile();
        for (String itemId : itemIds) {
            profile.editItemAttribute(itemId, "item_seen", true);
        }
    }
}

