package org.realityfn.fortnite.core.game.commands.mtx;

import net.sf.oval.constraint.NotNull;
import org.realityfn.fortnite.core.annotations.ProfileOperation;
import org.realityfn.fortnite.core.enums.MtxPlatform;
import org.realityfn.fortnite.core.enums.ProfileRoute;
import org.realityfn.fortnite.core.game.commands.OperationContext;
import org.realityfn.fortnite.core.game.commands.ProfileCommand;
import org.realityfn.fortnite.core.models.profiles.Profile;
import org.realityfn.fortnite.core.models.profiles.items.ProfileItem;

@ProfileOperation(operation = "RemoveGiftBox", profiles = "common_core", routes = ProfileRoute.CLIENT)
public class RemoveGiftBox extends ProfileCommand {

    @NotNull
    String[] giftBoxItemIds;

    @Override
    public void invokeProfileCommand(OperationContext operationContext) {
        Profile profile = operationContext.getPrimaryProfile();
        for (String giftBoxItemId : giftBoxItemIds) {
            ProfileItem giftBoxItem = profile.getItem(giftBoxItemId);
            if (giftBoxItem.getTemplateId().startsWith("GiftBox:")) {
                profile.removeItem(giftBoxItemId);
            }
        }
    }
}

