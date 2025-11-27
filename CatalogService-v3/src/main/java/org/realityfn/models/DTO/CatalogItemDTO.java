package org.realityfn.models.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.realityfn.models.items.apiobjects.Category;
import org.realityfn.models.items.apiobjects.CustomAttribute;
import org.realityfn.models.items.apiobjects.KeyImage;
import org.realityfn.models.items.apiobjects.ReleaseInfo;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CatalogItemDTO {

    private String id;
    private String title;
    private String description;
    private List<KeyImage> keyImages;
    private List<Category> categories;
    private String namespace;
    private String status;
    private String creationDate;
    private String lastModifiedDate;
    private Map<String, CustomAttribute> customAttributes;
    private String entitlementName;
    private String entitlementType;
    private String itemType;
    private List<String> dlcItemIds;
    private List<ReleaseInfo> releaseInfo;
    private String developer;
    private String developerId;
    private Integer useCount;
    private List<String> eulaIds;
    private Boolean endOfSupport;
    private Map<String, Object> ageGatings;
    private String applicationId;
    private Boolean unsearchable;

    public CatalogItemDTO() {}

    public CatalogItemDTO(
            String id,
            String title,
            String description,
            List<KeyImage> keyImages,
            List<Category> categories,
            String namespace,
            String status,
            String creationDate,
            String lastModifiedDate,
            Map<String, CustomAttribute> customAttributes,
            String entitlementName,
            String entitlementType,
            String itemType,
            List<String> dlcItemIds,
            List<ReleaseInfo> releaseInfo,
            String developer,
            String developerId,
            Integer useCount,
            List<String> eulaIds,
            Boolean endOfSupport,
            Map<String, Object> ageGatings,
            String applicationId,
            Boolean unsearchable
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.keyImages = keyImages;
        this.categories = categories;
        this.namespace = namespace;
        this.status = status;
        this.creationDate = creationDate;
        this.lastModifiedDate = lastModifiedDate;
        this.customAttributes = customAttributes;
        this.entitlementName = entitlementName;
        this.entitlementType = entitlementType;
        this.itemType = itemType;
        this.dlcItemIds = dlcItemIds;
        this.releaseInfo = releaseInfo;
        this.developer = developer;
        this.developerId = developerId;
        this.useCount = useCount;
        this.eulaIds = eulaIds;
        this.endOfSupport = endOfSupport;
        this.ageGatings = ageGatings;
        this.applicationId = applicationId;
        this.unsearchable = unsearchable;
    }


    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<KeyImage> getKeyImages() {
        return keyImages;
    }

    public void setKeyImages(List<KeyImage> keyImages) {
        this.keyImages = keyImages;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Map<String, CustomAttribute> getCustomAttributes() {
        return customAttributes;
    }


    public String getEntitlementName() {
        return entitlementName;
    }

    public String getEntitlementType() {
        return entitlementType;
    }

    public String getItemType() {
        return itemType;
    }

    public List<String> getDlcItemIds() {
        return dlcItemIds;
    }

    public List<ReleaseInfo> getReleaseInfo() {
        return releaseInfo;
    }

    public String getDeveloper() {
        return developer;
    }

    public String getDeveloperId() {
        return developerId;
    }

    public Integer getUseCount() {
        return useCount;
    }

    public List<String> getEulaIds() {
        return eulaIds;
    }

    public Boolean getEndOfSupport() {
        return endOfSupport;
    }

    public Map<String, Object> getAgeGatings() {
        return ageGatings;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public Boolean getUnsearchable() {
        return unsearchable;
    }
}