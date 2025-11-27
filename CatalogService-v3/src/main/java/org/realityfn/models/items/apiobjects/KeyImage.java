package org.realityfn.models.items.apiobjects;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class KeyImage {
    @JsonProperty("type")
    private String type;

    @JsonProperty("url")
    private String url;

    @JsonProperty("md5")
    private String md5;

    @JsonProperty("width")
    private Integer width;

    @JsonProperty("height")
    private Integer height;

    @JsonProperty("size")
    private Long size;

    @JsonProperty("uploadedDate")
    private String uploadedDate;

    public KeyImage() {}

    // Getters and Setters
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getMd5() { return md5; }
    public void setMd5(String md5) { this.md5 = md5; }

    public Integer getWidth() { return width; }
    public void setWidth(Integer width) { this.width = width; }

    public Integer getHeight() { return height; }
    public void setHeight(Integer height) { this.height = height; }

    public Long getSize() { return size; }
    public void setSize(Long size) { this.size = size; }

    public String getUploadedDate() { return uploadedDate; }
    public void setUploadedDate(String uploadedDate) { this.uploadedDate = uploadedDate; }
}
