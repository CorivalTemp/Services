package org.realityfn.models.mongo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.realityfn.models.items.apiobjects.Category;
import org.realityfn.models.items.apiobjects.CustomAttribute;
import org.realityfn.models.items.apiobjects.KeyImage;
import org.realityfn.models.items.apiobjects.ReleaseInfo;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CatalogItemMongo {

    @JsonProperty("_id")
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

    public CatalogItemMongo() {}

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

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Map<String, CustomAttribute> getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(Map<String, CustomAttribute> customAttributes) {
        this.customAttributes = customAttributes;
    }

    public String getEntitlementName() {
        return entitlementName;
    }

    public void setEntitlementName(String entitlementName) {
        this.entitlementName = entitlementName;
    }

    public String getEntitlementType() {
        return entitlementType;
    }

    public void setEntitlementType(String entitlementType) {
        this.entitlementType = entitlementType;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public List<String> getDlcItemIds() {
        return dlcItemIds;
    }

    public void setDlcItemIds(List<String> dlcItemIds) {
        this.dlcItemIds = dlcItemIds;
    }

    public List<ReleaseInfo> getReleaseInfo() {
        return releaseInfo;
    }

    public void setReleaseInfo(List<ReleaseInfo> releaseInfo) {
        this.releaseInfo = releaseInfo;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(String developerId) {
        this.developerId = developerId;
    }

    public Integer getUseCount() {
        return useCount;
    }

    public void setUseCount(Integer useCount) {
        this.useCount = useCount;
    }

    public List<String> getEulaIds() {
        return eulaIds;
    }

    public void setEulaIds(List<String> eulaIds) {
        this.eulaIds = eulaIds;
    }

    public Boolean getEndOfSupport() {
        return endOfSupport;
    }

    public void setEndOfSupport(Boolean endOfSupport) {
        this.endOfSupport = endOfSupport;
    }

    public Map<String, Object> getAgeGatings() {
        return ageGatings;
    }

    public void setAgeGatings(Map<String, Object> ageGatings) {
        this.ageGatings = ageGatings;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public Boolean getUnsearchable() {
        return unsearchable;
    }

    public void setUnsearchable(Boolean unsearchable) {
        this.unsearchable = unsearchable;
    }
}