package org.realityfn.fortnite.core.models.cloudstorage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "user_cloudstorage")
public class UserCloudstorageDataEntry {
    @Id
    private String id;

    private String accountId;
    private List<ConfigDataEntry> cloudstorageFiles = new ArrayList<>(); // Initialize to avoid null

    public UserCloudstorageDataEntry() {}

    public UserCloudstorageDataEntry(String accountId, List<ConfigDataEntry> cloudstorageFiles) {
        this.accountId = accountId;
        this.cloudstorageFiles = cloudstorageFiles != null ? cloudstorageFiles : new ArrayList<>();
    }

    public String getAccountId() {
        return accountId;
    }

    public List<ConfigDataEntry> getCloudstorageFiles() {
        return cloudstorageFiles;
    }

    public void setCloudstorageFiles(List<ConfigDataEntry> cloudstorageFiles) {
        this.cloudstorageFiles = cloudstorageFiles != null ? cloudstorageFiles : new ArrayList<>();
    }

    public void addFile(ConfigDataEntry file) {
        this.cloudstorageFiles.add(file);
    }

    public void removeFile(String uniqueFileName) {
        this.cloudstorageFiles.removeIf(file -> uniqueFileName.equals(file.getUniqueFileName()));
    }

    public boolean editFile(String uniqueFileName, String newData) {
        for (ConfigDataEntry file : this.cloudstorageFiles) {
            if (uniqueFileName.equals(file.getUniqueFileName())) {
                file.setData(newData);
                return true;
            }
        }
        return false;
    }
}
