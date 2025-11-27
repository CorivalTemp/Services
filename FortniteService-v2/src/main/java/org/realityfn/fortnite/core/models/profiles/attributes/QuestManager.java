package org.realityfn.fortnite.core.models.profiles.attributes;

import com.fasterxml.jackson.annotation.JsonInclude;

public class QuestManager {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String dailyLoginInterval;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String dailyQuestRerolls;
}
