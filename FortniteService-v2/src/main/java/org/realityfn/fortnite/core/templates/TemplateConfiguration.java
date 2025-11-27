package org.realityfn.fortnite.core.templates;

import org.realityfn.fortnite.core.models.profiles.Profile;
import org.realityfn.fortnite.core.models.profiles.items.Template;

public abstract class TemplateConfiguration {
    public abstract void initializeProfile(Profile profile, Template template);
    public abstract void updateProfile(Profile profile);
}
