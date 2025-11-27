package org.realityfn.fortnite.core.templates;

import org.realityfn.fortnite.core.config.BuildProperties;
import org.realityfn.fortnite.core.managers.profiles.TemplateManager;
import org.realityfn.fortnite.core.models.profiles.Profile;
import org.realityfn.fortnite.core.models.profiles.items.ProfileItem;
import org.realityfn.fortnite.core.models.profiles.items.Template;

import java.util.*;

public class AthenaTemplateConfiguration extends TemplateConfiguration
{
    private static final Template profileTemplate = TemplateManager.findProfileTemplateByType("athena");
    private static final String profileVersion = profileTemplate.getStat("static_migration");

    @Override
    public void initializeProfile(Profile profile, Template template)
    {
        String defaultPickaxeGUID = profile.addItem(FortniteTemplates.DEFAULT_PICKAXE_TEMPLATE).getItemId();
        String defaultGliderGUID = profile.addItem(FortniteTemplates.DEFAULT_GLIDER_TEMPLATE).getItemId();
        //String defaultDanceGUID = profile.addItem(FortniteTemplates.DEFAULT_DANCE_TEMPLATE).getItemId();
        profile.addItem(FortniteTemplates.DEFAULT_DANCE_TEMPLATE);
        String defaultLoadoutGUID = profile.addItem(FortniteTemplates.DEFAULT_LOADOUT_TEMPLATE).getItemId();

        // add default banner
        profile.changeStat("banner_icon", "standardbanner1");
        profile.changeStat("banner_color", "defaultcolor1");

        // add level stuff
        profile.changeStat("accountLevel", 1);
        profile.changeStat("level", 1);
        profile.changeStat("xp", 1);

        // battlepass
        profile.changeStat("book_purchased", false);
        profile.changeStat("book_level", 0);
        profile.changeStat("book_xp", 0);

        // season stuff
        profile.changeStat("season_num", BuildProperties.getCurrentSeason());
        profile.changeStat("season_match_boost", 0);
        profile.changeStat("season_friend_match_boost", 0);

        // default loadout
        ProfileItem loadoutItem = profile.getItem(defaultLoadoutGUID);
        HashMap<String, HashMap<String, Object>> slots = new HashMap<>();
        loadoutItem.setStat("locker_slots_data", slots);

        // cosmetics
        List<String> defaultDances = new ArrayList<>();
        for (int i = 0; i < 6; i++)
        {
            defaultDances.add("");
        }

        // loadout slots
        List<String> loadouts = new ArrayList<>();
        loadouts.add(defaultLoadoutGUID);
        loadouts.addAll(Collections.nCopies(10, null));
        profile.changeStat("loadouts", loadouts);

    }

    // Profile Update: 'reality_AddMultiPresetSupport_0001_2025' Adds missing loadout slots to all active profiles.
    @Override
    public void updateProfile(Profile profile) {
        if (profile.getVersion().equals(profileVersion)) {
            return;
        }
        List<String> loadouts = profile.getStat("loadouts");
        List<String> newLoadouts = new ArrayList<>(List.of());
        newLoadouts.addAll(loadouts);

        // Make the total exactly 11 indices (aka 10 slots + the main one '0')
        newLoadouts.addAll(Collections.nCopies(11 - loadouts.size(), null));
        profile.changeStat("loadouts", newLoadouts);
        profile.setVersion(profileVersion);
    }
}
