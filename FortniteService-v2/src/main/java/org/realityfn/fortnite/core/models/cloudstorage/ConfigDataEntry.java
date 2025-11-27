package org.realityfn.fortnite.core.models.cloudstorage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "system_cloudstorage")
public class ConfigDataEntry {
    @Id
    private String id;
    @Indexed(unique = true)
    private String uniqueFileName;
    private String fileName;
    private String data; // Base64 encoded binary data
    private String compressionMethod;
    private boolean hidden;
    private Instant uploaded;

    public ConfigDataEntry() {}

    public ConfigDataEntry(String uniqueFileName, String fileName, String data, String compressionMethod, boolean hidden, Instant uploaded) {
        this.uniqueFileName = uniqueFileName;
        this.fileName = fileName;
        this.data = data;
        this.compressionMethod = compressionMethod;
        this.hidden = hidden;
        this.uploaded = uploaded;
    }

    // Getters and setters ifykyk
    public String getUniqueFileName() {
        return uniqueFileName;
    }

    public String getFileName() {
        return fileName;
    }

    public String getData() {
        return data;
    }

    public String getCompressionMethod() {
        return compressionMethod;
    }

    public boolean getHidden() {
        return hidden;
    }

    public Instant getUploaded() {
        return uploaded;
    }

    public void setUniqueFileName(String uniqueFileName) {
        this.uniqueFileName = uniqueFileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setCompressionMethod(String compressionMethod) {
        this.compressionMethod = compressionMethod;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public void setUploaded(Instant uploaded) {
        this.uploaded = uploaded;
    }
}
