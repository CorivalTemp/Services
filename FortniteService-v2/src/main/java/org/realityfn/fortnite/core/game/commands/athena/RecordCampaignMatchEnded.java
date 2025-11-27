package org.realityfn.fortnite.core.game.commands.athena;

import org.realityfn.fortnite.core.annotations.ProfileOperation;
import org.realityfn.fortnite.core.enums.ProfileRoute;
import org.realityfn.fortnite.core.game.commands.OperationContext;
import org.realityfn.fortnite.core.game.commands.ProfileCommand;
import org.realityfn.fortnite.core.managers.profiles.ProfileManager;
import org.realityfn.fortnite.core.models.profiles.Profile;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@ProfileOperation(operation = "RecordCampaignMatchEnded", profiles = "athena", routes = ProfileRoute.CLIENT)
public class RecordCampaignMatchEnded extends ProfileCommand {

    @Override
    public void invokeProfileCommand(OperationContext context) {
        Profile athenaProfile = context.getPrimaryProfile();
        Profile commonCoreProfile = context.getProfile("common_core");

        if (commonCoreProfile.findItemByTemplateId("Token:campaignaccess") == null) {
            athenaProfile.changeStat("last_stw_match_end_datetime", Instant.now().truncatedTo(ChronoUnit.MILLIS).toString());
        }
    }
}