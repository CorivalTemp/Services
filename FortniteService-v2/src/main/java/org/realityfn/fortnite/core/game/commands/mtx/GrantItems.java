package org.realityfn.fortnite.core.game.commands.mtx;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.sf.oval.constraint.AssertValid;
import net.sf.oval.constraint.NotNull;
import org.realityfn.fortnite.core.annotations.ProfileOperation;
import org.realityfn.fortnite.core.enums.ProfileRoute;
import org.realityfn.fortnite.core.exceptions.modules.profiles.TemplateNotFound;
import org.realityfn.fortnite.core.game.commands.OperationContext;
import org.realityfn.fortnite.core.game.commands.ProfileCommand;
import org.realityfn.fortnite.core.managers.profiles.ProfileManager;
import org.realityfn.fortnite.core.managers.profiles.TemplateManager;
import org.realityfn.fortnite.core.models.profiles.Profile;
import org.realityfn.fortnite.core.models.profiles.ProfileChangeRequest;
import org.realityfn.fortnite.core.models.profiles.items.CreateItemDetails;
import org.realityfn.fortnite.core.models.profiles.items.Template;
import org.realityfn.fortnite.core.models.profiles.notifications.LootItem;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ProfileOperation(operation = "GrantItems", profiles = "common_core", routes = ProfileRoute.CHEAT)
public class GrantItems extends ProfileCommand {

    @NotNull
    @AssertValid
    List<CreateItemDetails> itemsToGrant;

    @Override
    public void invokeProfileCommand(OperationContext operationContext) {
        Profile profile = operationContext.getPrimaryProfile();

        ArrayList<LootItem> lootList = new ArrayList<>();
        for (CreateItemDetails itemDetails : itemsToGrant) {
            String templateId = itemDetails.getTemplateId();

            Template template = TemplateManager.getTemplate(templateId);

            if (template == null) {
                throw new TemplateNotFound(templateId);
            }
            /* We use addItem instead of grantItem due to our need for the item guid
                please only use this on purchases or grants as it doesn't validate any template attributes
             */
            String templateProfile = template.getTemplateProfile();
            Profile profileToGrantTo = operationContext.getProfile(templateProfile);
            ProfileChangeRequest.AddItemRequest addItemRequest = profileToGrantTo.addItem(template, itemDetails.getAttributes(), itemDetails.getQuantity());

            LootItem lootItem = new LootItem(
                    addItemRequest.getItemId(),
                    templateProfile,
                    templateId,
                    itemDetails.getQuantity()
            );
            lootList.add(lootItem);
        }
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("lootList", lootList);
        profile.grantItem(TemplateManager.getTemplate("GiftBox:gb_rmtoffer"),attributes, 1);
    }
}
