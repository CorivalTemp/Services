package org.realityfn.fortnite.core.models.timeline;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TimelineChannel {
    @JsonProperty("states")
    public ArrayList<ChannelState> States;

    @JsonProperty("cacheExpire")
    public String CacheExpire;

    public TimelineChannel(ArrayList<ChannelState> states) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(java.time.ZoneId.of("UTC"));

        this.States = states;
        this.CacheExpire = dtf.format(java.time.Instant.now().plusSeconds(60 * 15));
    }
}
