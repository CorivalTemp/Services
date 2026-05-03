package org.realityfn.models.account.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreatedData {
    @JsonProperty("location")
    public String Location;

    @JsonProperty("ipAddress")
    public String IpAddress;

    @JsonProperty("dateTime")
    public String DateTime;
}
