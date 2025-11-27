package org.realityfn.fortnite.core.game.commands.athena;

import org.realityfn.fortnite.core.annotations.ProfileOperation;
import org.realityfn.fortnite.core.enums.ProfileRoute;
import org.realityfn.fortnite.core.game.commands.ProfileCommand;

@ProfileOperation(operation = "SetHardcoreModifier", profiles = "athena", routes = ProfileRoute.CLIENT)
public class SetHardcoreModifier extends ProfileCommand {
}
