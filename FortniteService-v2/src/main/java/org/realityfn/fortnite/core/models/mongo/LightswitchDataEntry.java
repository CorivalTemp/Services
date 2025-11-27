package org.realityfn.fortnite.core.models.mongo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.realityfn.fortnite.core.models.lightswitch.LightswitchServiceStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import static org.realityfn.fortnite.core.config.BuildProperties.APP;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "accounts")
public class LightswitchDataEntry {
    @Id
    @JsonProperty("_id")
    private String id;

    @JsonProperty("services")
    private List<LightswitchServiceStatus> services;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<LightswitchServiceStatus> getServices() {
        return services;
    }

    public void setServices(List<LightswitchServiceStatus> services) {
        this.services = services;
    }
}