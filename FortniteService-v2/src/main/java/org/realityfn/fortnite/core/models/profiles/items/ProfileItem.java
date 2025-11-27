package org.realityfn.fortnite.core.models.profiles.items;


import net.sf.oval.constraint.NotNull;

public class ProfileItem extends Template
{
    @NotNull
    private int quantity;

    public ProfileItem()
    { }

    public ProfileItem(Template itemTemplate)
    {
        this.setTemplateId(itemTemplate.getTemplateId());
        this.quantity = 1;
    }

    public ProfileItem(Template itemTemplate, int quantity)
    {
        this.setTemplateId(itemTemplate.getTemplateId());
        this.quantity = quantity;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }
}