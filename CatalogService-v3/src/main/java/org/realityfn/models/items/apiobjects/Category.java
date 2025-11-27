package org.realityfn.models.items.apiobjects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Category {
    @JsonProperty("path")
    private String path;

    public Category() {}

    public Category(String path) {
        this.path = path;
    }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
}
