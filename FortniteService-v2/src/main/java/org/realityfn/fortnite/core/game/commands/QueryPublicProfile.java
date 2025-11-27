package org.realityfn.fortnite.core.game.commands;

import org.realityfn.fortnite.core.annotations.ProfileOperation;
import org.realityfn.fortnite.core.enums.ProfileRoute;

@ProfileOperation(operation = "QueryPublicProfile", profiles = {"common_public", "campaign"}, routes = ProfileRoute.PUBLIC, readOnly = true, nonAllowCreate = false)
public class QueryPublicProfile extends ProfileCommand {}

