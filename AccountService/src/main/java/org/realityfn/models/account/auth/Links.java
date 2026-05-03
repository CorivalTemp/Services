package org.realityfn.models.account.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Links {
    @JsonProperty("youtube")
    public String Youtube;

    @JsonProperty("twitch")
    public String Twitch;
}
