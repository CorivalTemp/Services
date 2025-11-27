package org.realityfn.fortnite.core.game.commands.athena;

import net.sf.oval.constraint.NotNull;
import org.realityfn.fortnite.core.annotations.ProfileOperation;
import org.realityfn.fortnite.core.enums.Category;
import org.realityfn.fortnite.core.enums.ProfileRoute;
import org.realityfn.fortnite.core.exceptions.modules.profiles.InvalidItemIdException;
import org.realityfn.fortnite.core.exceptions.modules.profiles.InvalidTemplateIdException;
import org.realityfn.fortnite.core.game.commands.OperationContext;
import org.realityfn.fortnite.core.game.commands.ProfileCommand;
import org.realityfn.fortnite.core.managers.profiles.TemplateManager;
import org.realityfn.fortnite.core.models.profiles.Profile;
import org.realityfn.fortnite.core.models.profiles.items.ActiveVariantsLoadout;
import org.realityfn.fortnite.core.models.profiles.items.ProfileItem;
import org.realityfn.fortnite.core.models.profiles.items.Template;
import org.realityfn.fortnite.core.models.profiles.items.VariantUpdates;

import java.util.*;


// Full Style support is not yet ready
@SuppressWarnings("unchecked")
@ProfileOperation(operation = "SetCosmeticLockerSlot", profiles = "athena", routes = ProfileRoute.CLIENT)
public class SetCosmeticLockerSlot extends ProfileCommand {

    @NotNull
    String lockerItem;

    @NotNull
    Category category;

    String itemToSlot;

    @NotNull
    int slotIndex;

    @NotNull
    List<VariantUpdates> variantUpdates;

    @NotNull
    int optLockerUseCountOverride;


    @Override
    public void invokeProfileCommand(OperationContext operationContext) {
        Profile profile = operationContext.getPrimaryProfile();

        ProfileItem fullLockerItem = profile.getItem(lockerItem);

        if (fullLockerItem == null || !fullLockerItem.getTemplateId().toLowerCase().startsWith("cosmeticlocker:")) {
            throw new InvalidItemIdException(lockerItem);
        }

        if (!itemToSlot.isEmpty() && !itemToSlot.matches("^(Athena[a-zA-Z]+).*:.*(_random)$")) {
            ProfileItem targetItem = profile.findItemByTemplateId(itemToSlot);
            if (targetItem == null) {
                throw new InvalidTemplateIdException(itemToSlot);
            }
        }

        Template template = TemplateManager.getTemplate(fullLockerItem.getTemplateId());
        assert template != null;

        // Get or initialize locker slots data ONCE at the beginning
        Map<String, Object> lockerSlotData = fullLockerItem.getStat("locker_slots_data");
        if (lockerSlotData == null) {
            lockerSlotData = new HashMap<>();
            lockerSlotData.put("slots", new HashMap<>());
        }

        if (!lockerSlotData.containsKey("slots") || lockerSlotData.get("slots") == null) {
            lockerSlotData.put("slots", new HashMap<String, Object>());
        }

        Map<String, Object> slots = (Map<String, Object>) lockerSlotData.get("slots");

        int numberOfSlots = 1;
        Map<String, Object> slotData = null;

        for (HashMap<String, Object> staticLoadoutData : (List<HashMap<String, Object>>) template.getStat("static_locker_slots_data")) {
            if (category.toString().equals(staticLoadoutData.get("customization_category"))) {
                numberOfSlots = (Integer) staticLoadoutData.getOrDefault("num_slots_of_category", 1);

                // Get existing slot data for this category or create new
                if (slots.containsKey(category.toString())) {
                    slotData = (Map<String, Object>) slots.get(category.toString());
                } else {
                    // Category doesn't exist yet
                    slotData = new HashMap<>();
                    slotData.put("items", new String[numberOfSlots]);
                    slotData.put("activeVariants", new ActiveVariantsLoadout[numberOfSlots]);
                }
                break;
            }
        }

        List<String> items;
        Object itemsObj = slotData.get("items");
        if (itemsObj instanceof List) {
            List<String> itemsList = (List<String>) itemsObj;
            items = new ArrayList<>(itemsList);
        } else if (itemsObj instanceof String[]) {
            items = new ArrayList<>(Arrays.asList((String[]) itemsObj));
        } else {
            items = new ArrayList<>(Collections.nCopies(numberOfSlots, null));
        }

        List<ActiveVariantsLoadout> activeVariants;
        Object variantsObj = slotData.get("activeVariants");
        if (variantsObj instanceof List) {
            List<ActiveVariantsLoadout> variantsList = (List<ActiveVariantsLoadout>) variantsObj;
            activeVariants = new ArrayList<>(variantsList);
        } else if (variantsObj instanceof ActiveVariantsLoadout[]) {
            activeVariants = new ArrayList<>(Arrays.asList((ActiveVariantsLoadout[]) variantsObj));
        } else {
            activeVariants = new ArrayList<>(Collections.nCopies(numberOfSlots, null));
        }

        // Apply the slot changes
        if (slotIndex == -1 || slotIndex >= numberOfSlots) {
            // Set all slots
            for (int i = 0; i < numberOfSlots; i++) {
                items.set(i, itemToSlot);

                if (!variantUpdates.isEmpty() && i < variantUpdates.size()) {
                    VariantUpdates variantUpdate = variantUpdates.get(i);
                    if (variantUpdate != null && variantUpdate.getChannel() != null && variantUpdate.getActive() != null) {
                        ActiveVariantsLoadout.Variant variant = new ActiveVariantsLoadout.Variant(
                                variantUpdate.getChannel(),
                                variantUpdate.getActive()
                        );
                        activeVariants.set(i, new ActiveVariantsLoadout(List.of(variant)));
                    } else {
                        activeVariants.set(i, null);
                    }
                } else {
                    activeVariants.set(i, null);
                }
            }
        } else {
            // Set specific slot
            items.set(slotIndex, itemToSlot);

            if (!variantUpdates.isEmpty() && slotIndex < variantUpdates.size()) {
                VariantUpdates variantUpdate = variantUpdates.get(slotIndex);
                if (variantUpdate != null && variantUpdate.getChannel() != null && variantUpdate.getActive() != null) {
                    ActiveVariantsLoadout.Variant variant = new ActiveVariantsLoadout.Variant(
                            variantUpdate.getChannel(),
                            variantUpdate.getActive()
                    );
                    activeVariants.set(slotIndex, new ActiveVariantsLoadout(List.of(variant)));
                } else {
                    activeVariants.set(slotIndex, null);
                }
            } else {
                activeVariants.set(slotIndex, null);
            }
        }

        slotData.put("items", items);
        slotData.put("activeVariants", activeVariants);

        slots.put(category.toString(), slotData);
        lockerSlotData.put("slots", slots);

        Map<String, Object> newLockerSlotData = new HashMap<>(lockerSlotData);

        profile.editItemAttribute(lockerItem, "locker_slots_data", newLockerSlotData);

    }
}