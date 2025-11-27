package org.realityfn.fortnite.core.models.profiles;


import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import io.micrometer.common.util.StringUtils;
import org.realityfn.common.exceptions.common.ServerErrorException;
//import org.realityfn.fortnite.core.errorhandling.InvalidProfileTypeException;
//import org.realityfn.fortnite.core.errorhandling.MinimumItemQuantityException;
//import org.realityfn.fortnite.core.errorhandling.ReachedMaxNumStacksException;
//import org.realityfn.fortnite.core.errorhandling.UnallowedItemAttributeException;

import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.realityfn.fortnite.core.exceptions.modules.profiles.InvalidProfileTypeException;
import org.realityfn.fortnite.core.exceptions.modules.profiles.MinimumItemQuantityException;
import org.realityfn.fortnite.core.exceptions.modules.profiles.ReachedMaxNumStacksException;
import org.realityfn.fortnite.core.exceptions.modules.profiles.UnallowedItemAttributeException;
import org.realityfn.fortnite.core.models.profiles.items.ProfileItem;
import org.realityfn.fortnite.core.models.profiles.items.Template;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value = "_id")
public class Profile
{
    @JsonProperty("_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    private String created;

    private String updated;

    private int rvn;

    private int wipeNumber;

    private String accountId;

    private String profileId;

    private String version;

    private HashMap<String, ProfileItem> items;

    private ProfileAttributes stats;

    private String profileLockCode;

    private String profileLockExpiration;

    private int commandRevision;

    @BsonIgnore
    @JsonIgnore
    private List<ProfileChangeRequest> changes = new ArrayList<>();

    public Profile()
    { }

    public Profile(String profileId, String accountId)
    {
        this.id = UUID.randomUUID().toString().replace("-", "");
        this.created = Instant.now().truncatedTo(ChronoUnit.MILLIS).toString();
        this.updated = Instant.now().truncatedTo(ChronoUnit.MILLIS).toString();
        this.rvn = 1;
        this.wipeNumber = 4;
        this.accountId = accountId;
        this.profileId = profileId;
        this.items = new HashMap<>();
        this.stats = new ProfileAttributes();
        this.commandRevision = 0;
    }

    public void applyProfileLock(String lockCode, int time)
    {
        this.profileLockCode = lockCode;
        this.profileLockExpiration = String.valueOf(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS).plusSeconds(time));
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getCreated()
    {
        return created;
    }

    public void setCreated(String created)
    {
        this.created = created;
    }

    public int getWipeNumber()
    {
        return wipeNumber;
    }

    public void setWipeNumber(int wipeNumber)
    {
        this.wipeNumber = wipeNumber;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    @JsonProperty("rvn")
    public int getRevision()
    {
        return rvn;
    }

    public void setRevision(int rvn)
    {
        this.rvn = rvn;
    }

    public String getAccountId()
    {
        return accountId;
    }

    public void setAccountId(String accountId)
    {
        this.accountId = accountId;
    }

    public String getProfileId()
    {
        return profileId;
    }

    public void setProfileId(String profileId)
    {
        this.profileId = profileId;
    }

    public ProfileAttributes getStats()
    {
        return stats;
    }

    public void setStats(ProfileAttributes stats)
    {
        this.stats = stats;
    }

    public Map<String, ProfileItem> getItems()
    {
        return items;
    }

    public void setItems(HashMap<String, ProfileItem> items)
    {
        this.items = items;
    }

    public int getCommandRevision()
    {
        return commandRevision;
    }

    public void setCommandRevision(int commandRevision)
    {
        this.commandRevision = commandRevision;
    }

    public String getProfileLockCode()
    {
        return profileLockCode;
    }

    public void setProfileLockCode(String profileLockCode)
    {
        this.profileLockCode = profileLockCode;
    }

    public String getProfileLockExpiration()
    {
        return profileLockExpiration;
    }

    public void setProfileLockExpiration(String profileLockExpiration)
    {
        this.profileLockExpiration = profileLockExpiration;
    }

    public String getUpdated()
    {
        return updated;
    }

    public void setUpdated(String updated)
    {
        this.updated = updated;
    }

    public List<ProfileChangeRequest> getChanges()
    {
        return changes;
    }

    public void setChanges(List<ProfileChangeRequest> changes)
    {
        this.changes = changes;
    }

    public void updateRevision()
    {
        rvn++;
    }

    public void updateCommandRevision()
    {
        commandRevision++;
    }

    public ProfileChangeRequest.AddItemRequest addItem(Template itemTemplate, Map<String, Object> attributes, int quantity)
    {
        String itemGUID = UUID.randomUUID().toString();

        // create item
        ProfileItem item = new ProfileItem();
        {
            item.setTemplateId(itemTemplate.getTemplateId());
            item.setQuantity(quantity);

            if (attributes == null) attributes = new HashMap<>();
            attributes.forEach(item::setStat);

            items.put(itemGUID, item);
        }

        ProfileChangeRequest.AddItemRequest addItemRequest
                = new ProfileChangeRequest.AddItemRequest(itemGUID, item);

        changes.add(addItemRequest);

        return addItemRequest;
    }

    public ProfileChangeRequest.AddItemRequest addItem(Template itemTemplate)
    {
        return addItem(itemTemplate, null, 1);
    }

    public void grantItem(Template itemTemplate, Map<String, Object> attributes, int quantity)
    {
        if (attributes == null) attributes = new HashMap<>();

        String profileType = itemTemplate.getStat("static_profile_type");
        if ( !StringUtils.isBlank(profileType) && !profileType.equals(profileId) )
        {
            throw new InvalidProfileTypeException(itemTemplate.getTemplateId(), profileId);
        }

        if ( quantity < 1 )
        {
            throw new MinimumItemQuantityException(quantity, 1);
        }

        Map<String, Object> itemTemplateAttributes = itemTemplate.getAttributes();
        attributes.forEach((key, val) ->
        {
            if ( key.startsWith("static_") || !itemTemplateAttributes.containsKey(key) )
            {
                throw new UnallowedItemAttributeException(key, itemTemplate.getTemplateId());
            }
        });

        int maxStackSize = itemTemplate.getStatOrDefault("static_max_stack_size", 1);
        int maxNumStacks = itemTemplate.getStatOrDefault("static_max_num_stacks", 1);

        Map<String, ProfileItem> existingItems = findItemsByTemplateId(itemTemplate.getTemplateId());

        int currentNumStacks = existingItems.size();

        if ( !existingItems.isEmpty() )
        {
            String newestStackGUID = null;
            ProfileItem newestStack = null;

            // find newest stack
            for ( Map.Entry<String, ProfileItem> entry : existingItems.entrySet() )
            {
                String itemGUID = entry.getKey();
                ProfileItem item = entry.getValue();

                if ( item.getQuantity() < maxStackSize )
                {
                    newestStackGUID = itemGUID;
                    newestStack = item;
                    break;
                }
            }

            // try adding quantity to existing stack
            if ( newestStack != null )
            {
                int newStackSize = 0;

                try
                {
                    newStackSize = Math.addExact(newestStack.getQuantity(), quantity);
                }
                catch (ArithmeticException exception)
                {
                    // integer overflow, set stack size to MAX_INT
                    newStackSize = Integer.MAX_VALUE;
                }

                // stack's new quantity exceeds max stack size
                // fill stack and try adding a new stack in while loop
                if ( newStackSize > maxStackSize )
                {
                    quantity = newStackSize - (maxStackSize - newestStack.getQuantity());

                    // fill existing stack
                    changeItemQuantity(newestStackGUID, maxStackSize);
                }
                // fill stack and return
                else
                {
                    changeItemQuantity(newestStackGUID, newStackSize);
                    return;
                }
            }
        }

        while ( quantity != 0 )
        {
            // are we allowed to add a new stack?
            if ( currentNumStacks + 1 <= maxNumStacks )
            {
                // are we exceeding stack quantity?
                if ( quantity > maxStackSize )
                {
                    quantity -= maxStackSize;

                    addItem(itemTemplate, attributes, maxStackSize);
                }
                // we are done adding stacks
                else
                {
                    addItem(itemTemplate, attributes, quantity);
                    break;
                }
            }
            else
            {
                throw new ReachedMaxNumStacksException(itemTemplate.getTemplateId(), existingItems.size(), quantity / maxStackSize, maxNumStacks);
            }

            currentNumStacks++;
        }
    }

    public void grantItem(Template itemTemplate)
    {
        grantItem(itemTemplate, null, 1);
    }

    public ProfileChangeRequest.RemoveItemRequest removeItem(String itemGUID)
    {
        if (items.containsKey(itemGUID))
        {
            items.remove(itemGUID);

            ProfileChangeRequest.RemoveItemRequest removeItemRequest
                    = new ProfileChangeRequest.RemoveItemRequest(itemGUID);

            changes.add(removeItemRequest);

            return removeItemRequest;
        }

        return null;
    }

    public ProfileItem getItem(String itemGUID)
    {
        return items.get(itemGUID);
    }

    public ProfileItem findItemByTemplateId(String templateId)
    {
        for (ProfileItem item : items.values())
        {
            if (item.getTemplateId().equals(templateId))
            {
                return item;
            }
        }

        return null;
    }

    public Map<String, ProfileItem> findItemsByTemplateId(String templateId)
    {
        Map<String, ProfileItem> profileItems = new HashMap<>();

        for (Map.Entry<String, ProfileItem> entry : items.entrySet())
        {
            String itemGUID = entry.getKey();
            ProfileItem item = entry.getValue();

            if (item.getTemplateId().equals(templateId))
            {
                profileItems.put(itemGUID, item);
            }
        }

        return profileItems;
    }

    public boolean hasItem(String itemGUID)
    {
        return items.containsKey(itemGUID);
    }

    public ProfileChangeRequest.ChangeQuantityRequest.ChangeQuantityRequest changeItemQuantity(String itemGUID, int quantity)
    {
        ProfileItem item = items.get(itemGUID);
        if (item == null)
        {
            return null;
        }

        item.setQuantity(quantity);

        ProfileChangeRequest.ChangeQuantityRequest changeQuantityRequest
                = new ProfileChangeRequest.ChangeQuantityRequest(itemGUID, item.getTemplateId(), quantity);

        changes.add(changeQuantityRequest);

        return changeQuantityRequest;
    }

    public ProfileChangeRequest.ChangeAttributesRequest editItemAttribute(String itemGUID, String attr, Object attrVal)
    {
        ProfileItem item = items.get(itemGUID);
        if (item == null)
        {
            return null;
        }

        Object existingStat = item.getStat(attr);

        if ( existingStat == null || !existingStat.equals(attrVal) )
        {
            item.setStat(attr, attrVal);

            ProfileChangeRequest.ChangeAttributesRequest changeAttributesRequest
                    = new ProfileChangeRequest.ChangeAttributesRequest(itemGUID, attr, attrVal);

            changes.add(changeAttributesRequest);

            return changeAttributesRequest;
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> T getStat(String statName)
    {
        return (T) stats.getAttribute(statName);
    }

    //public <T> T getStatOrDefault(String statName, Object defaultValue)
    //{
    //    return stats.getAttributeOrDefault(statName, defaultValue);
    //}

    //public <T> List<T> getStatlist(String key, Class<T> target)
    //{
    //    return stats.getStatlist(key, target);
    //}

    public ProfileChangeRequest.ChangeStatRequest changeStat(String statName, Object statValue)
    {
        Object stat = stats.getAttribute(statName);

        if (stat == null || !stat.equals(statValue))
        {
            stats.setAttribute(statName, statValue);

            ProfileChangeRequest.ChangeStatRequest changeStatRequest
                    = new ProfileChangeRequest.ChangeStatRequest(statName, statValue);

            changes.add(changeStatRequest);

            return changeStatRequest;
        }

        return null;
    }
}
