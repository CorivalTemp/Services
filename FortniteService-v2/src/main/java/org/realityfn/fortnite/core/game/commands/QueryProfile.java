package org.realityfn.fortnite.core.game.commands;

import org.realityfn.fortnite.core.annotations.ProfileOperation;
import org.realityfn.fortnite.core.enums.ProfileRoute;

@ProfileOperation(operation = "QueryProfile", profiles = {}, routes = {ProfileRoute.CLIENT, ProfileRoute.CHEAT, ProfileRoute.DEDICATED_SERVER})
public class QueryProfile extends ProfileCommand {}

