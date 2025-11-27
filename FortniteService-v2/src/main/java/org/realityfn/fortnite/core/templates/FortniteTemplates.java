package org.realityfn.fortnite.core.templates;

import org.realityfn.fortnite.core.managers.profiles.TemplateManager;
import org.realityfn.fortnite.core.config.BuildProperties;
import org.realityfn.fortnite.core.models.profiles.items.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FortniteTemplates
{
    static Logger logger = LoggerFactory.getLogger(FortniteTemplates.class);

    public static Template SEASON_TEMPLATE;

    public static Template DEFAULT_PICKAXE_TEMPLATE;

    public static Template DEFAULT_GLIDER_TEMPLATE;

    public static Template DEFAULT_DANCE_TEMPLATE;

    public static Template DEFAULT_LOADOUT_TEMPLATE;

    public static boolean initialize()
    {
        String seasonTemplateId = "AthenaSeason:athenaseason" + BuildProperties.getCurrentSeason();

        SEASON_TEMPLATE = TemplateManager.getTemplate(seasonTemplateId);
        if ( SEASON_TEMPLATE == null )
        {
            logger.error("Unknown item template {}", seasonTemplateId);
            return false;
        }

        String defaultPickaxeTemplateId = "AthenaPickaxe:defaultpickaxe";

        DEFAULT_PICKAXE_TEMPLATE = TemplateManager.getTemplate(defaultPickaxeTemplateId);
        if ( DEFAULT_PICKAXE_TEMPLATE == null )
        {
            logger.error("Unknown item template {}", defaultPickaxeTemplateId);
            return false;
        }

        String defaultGliderTemplateId = "AthenaGlider:defaultglider";

        DEFAULT_GLIDER_TEMPLATE = TemplateManager.getTemplate(defaultGliderTemplateId);
        if ( DEFAULT_GLIDER_TEMPLATE == null )
        {
            logger.error("Unknown item template {}", defaultGliderTemplateId);
            return false;
        }

        String defaultDanceTemplateId = "AthenaDance:eid_dancemoves";

        DEFAULT_DANCE_TEMPLATE = TemplateManager.getTemplate(defaultDanceTemplateId);
        if ( DEFAULT_DANCE_TEMPLATE == null )
        {
            logger.error("Unknown item template {}", defaultDanceTemplateId);
            return false;
        }

        String defaultLoadoutTemplateId = "CosmeticLocker:cosmeticlocker_athena";

        DEFAULT_LOADOUT_TEMPLATE = TemplateManager.getTemplate(defaultLoadoutTemplateId);
        if ( DEFAULT_LOADOUT_TEMPLATE == null )
        {
            logger.error("Unknown item template {}", defaultLoadoutTemplateId);
            return false;
        }

        return true;
    }
}
