package org.realityfn.fortnite.core.templates;

import jakarta.ws.rs.BadRequestException;
import org.realityfn.fortnite.core.managers.profiles.TemplateManager;
import org.realityfn.fortnite.core.models.profiles.Profile;
import org.realityfn.fortnite.core.models.profiles.ProfileChangeRequest;
import org.realityfn.fortnite.core.models.profiles.items.Template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommonCoreTemplateConfiguration extends TemplateConfiguration
{
    @Override
    public void initializeProfile(Profile profile, Template template)
    {
        for (int idx = 1; idx < 32; idx++)
        {
            String templateId = "HomebaseBannerIcon:standardbanner" + idx;

            Template itemTemplate = TemplateManager.getTemplate(templateId);
            if (itemTemplate == null)
            {
                throw new BadRequestException("Invalid banner icon template.");
            }

            ProfileChangeRequest.AddItemRequest addItemRequest = profile.addItem(itemTemplate);
            if (addItemRequest == null)
            {
                throw new BadRequestException("Failed to add banner icon item to profile.");
            }

            // hack to reset item attributes
            addItemRequest.getItem().setAttributes(new HashMap<>());

            String itemGUID = addItemRequest.getItemId();

            profile.editItemAttribute(itemGUID, "item_seen", true);
        }

        for (int idx = 1; idx < 22; idx++)
        {
            String templateId = "HomebaseBannerColor:defaultcolor" + idx;

            Template itemTemplate = TemplateManager.getTemplate(templateId);
            if (itemTemplate == null)
            {
                throw new BadRequestException("Invalid banner color template.");
            }

            ProfileChangeRequest.AddItemRequest addItemRequest = profile.addItem(itemTemplate);
            if (addItemRequest == null)
            {
                throw new BadRequestException("Failed to add banner color item to profile.");
            }

            // hack to reset item attributes
            addItemRequest.getItem().setAttributes(new HashMap<>());

            String itemGUID = addItemRequest.getItemId();

            profile.editItemAttribute(itemGUID, "item_seen", true);
        }

        profile.changeStat("allowed_to_receive_gifts", true);
        profile.changeStat("allowed_to_send_gifts", true);

    }

    @Override
    public void updateProfile(Profile profile) {}
}