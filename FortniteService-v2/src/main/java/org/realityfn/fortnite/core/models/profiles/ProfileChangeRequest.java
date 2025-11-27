package org.realityfn.fortnite.core.models.profiles;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.realityfn.fortnite.core.models.profiles.items.ProfileItem;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileChangeRequest
{
    String changeType;

    public static class FullUpdateRequest extends ProfileChangeRequest
    {
        private Profile profile;

        public FullUpdateRequest(Profile profile)
        {
            this.changeType = "fullProfileUpdate";

            this.profile = profile;
        }

        public Profile getProfile()
        {
            return profile;
        }

        public void setProfile(Profile profile)
        {
            this.profile = profile;
        }
    }

    public static class AddItemRequest extends ProfileChangeRequest
    {
        private String itemId;

        private ProfileItem item;

        public AddItemRequest(String itemId, ProfileItem item)
        {
            this.changeType = "itemAdded";

            this.itemId = itemId;
            this.item = item;
        }

        public String getItemId()
        {
            return itemId;
        }

        public void setItemId(String itemId)
        {
            this.itemId = itemId;
        }

        public ProfileItem getItem()
        {
            return item;
        }

        public void setItem(ProfileItem item)
        {
            this.item = item;
        }
    }

    public static class RemoveItemRequest extends ProfileChangeRequest
    {
        private String itemId;

        public RemoveItemRequest(String itemId)
        {
            this.changeType = "itemRemoved";

            this.itemId = itemId;
        }

        public String getItemId()
        {
            return itemId;
        }

        public void setItemId(String itemId)
        {
            this.itemId = itemId;
        }
    }

    public static class ChangeQuantityRequest extends ProfileChangeRequest
    {
        private String item;

        private String templateId;

        private int quantity;

        public ChangeQuantityRequest(String item, String templateId, int quantity)
        {
            this.changeType = "itemQuantityChanged";

            this.item = item;
            this.templateId = templateId;
            this.quantity = quantity;
        }

        public String getItem()
        {
            return item;
        }

        public void setItem(String item)
        {
            this.item = item;
        }

        public String getTemplateId()
        {
            return templateId;
        }

        public void setTemplateId(String templateId)
        {
            this.templateId = templateId;
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

    public static class ChangeAttributesRequest extends ProfileChangeRequest
    {
        private String itemId;

        private String attributeName;

        private Object attributeValue;

        public ChangeAttributesRequest(String itemId, String attributeName, Object attributeValue)
        {
            this.changeType = "itemAttrChanged";

            this.itemId = itemId;
            this.attributeName = attributeName;
            this.attributeValue = attributeValue;
        }

        public String getItemId()
        {
            return itemId;
        }

        public void setItemId(String itemId)
        {
            this.itemId = itemId;
        }

        public String getAttributeName()
        {
            return attributeName;
        }

        public void setAttributeName(String attributeName)
        {
            this.attributeName = attributeName;
        }

        public Object getAttributeValue()
        {
            return attributeValue;
        }

        public void setAttributeValue(Object attributeValue)
        {
            this.attributeValue = attributeValue;
        }
    }

    public static class ChangeStatRequest extends ProfileChangeRequest
    {
        private String name;

        private Object value;

        public ChangeStatRequest(String name, Object value)
        {
            this.changeType = "statModified";

            this.name = name;
            this.value = value;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public Object getValue()
        {
            return value;
        }

        public void setValue(Object value)
        {
            this.value = value;
        }
    }

    public String getChangeType() {
        return changeType;
    }
}
