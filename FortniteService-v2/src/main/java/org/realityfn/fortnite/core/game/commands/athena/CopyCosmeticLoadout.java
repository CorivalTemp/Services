package org.realityfn.fortnite.core.game.commands.athena;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotNull;
import org.realityfn.fortnite.core.annotations.ProfileOperation;
import org.realityfn.fortnite.core.enums.ProfileRoute;
import org.realityfn.fortnite.core.exceptions.fortnite.CosmeticsInvalidLockerSlotIndexException;
import org.realityfn.fortnite.core.exceptions.fortnite.OutOfBoundsException;
import org.realityfn.fortnite.core.game.commands.OperationContext;
import org.realityfn.fortnite.core.game.commands.ProfileCommand;
import org.realityfn.fortnite.core.managers.profiles.TemplateManager;
import org.realityfn.fortnite.core.models.profiles.Profile;
import org.realityfn.fortnite.core.models.profiles.ProfileChangeRequest;
import org.realityfn.fortnite.core.models.profiles.items.ProfileItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ProfileOperation(operation = "CopyCosmeticLoadout", profiles = {"athena", "campaign"}, routes = ProfileRoute.CLIENT, forceFullProfileUpdate = true)
public class CopyCosmeticLoadout extends ProfileCommand {
    @NotNull
    int sourceIndex;

    @NotNull
    int targetIndex;

    @Length(max = 16)
    @JsonIgnoreProperties(ignoreUnknown = true)
    String optNewNameForTarget;

    @Override
    public void invokeProfileCommand(OperationContext operationContext) {
        Profile profile = operationContext.getPrimaryProfile();
        ArrayList<String> loadouts = profile.getStat("loadouts");
        if (targetIndex >= loadouts.size()) {
            throw new OutOfBoundsException("Invalid loadout index {} (number of loadouts: {})", targetIndex, loadouts.size());
        }
        if (loadouts.get(sourceIndex) == null) {
            throw new CosmeticsInvalidLockerSlotIndexException(sourceIndex);
        }
        ProfileItem mainLoadout = profile.getItem(loadouts.get(sourceIndex));
        Map<String, Object> mainLockerSlotsData = mainLoadout.getStat("locker_slots_data");


        // Check if a loadout exists in the slot and create one if one doesn't
        String newLoadoutId;
        ProfileItem newLoadout;
        if (loadouts.get(targetIndex) == null) {
            ProfileChangeRequest.AddItemRequest addedItem = profile.addItem(TemplateManager.getTemplate("CosmeticLocker:cosmeticlocker_athena"));
            newLoadoutId = addedItem.getItemId();
            newLoadout = addedItem.getItem();
        }
        else {
            newLoadoutId = loadouts.get(targetIndex);
            newLoadout = profile.getItem(newLoadoutId);
        }
        loadouts.set(targetIndex, newLoadoutId);
        profile.changeStat("loadouts", loadouts);
        newLoadout.setStat("locker_slots_data", mainLockerSlotsData);
        newLoadout.setStat("locker_name", optNewNameForTarget);
    }
}
