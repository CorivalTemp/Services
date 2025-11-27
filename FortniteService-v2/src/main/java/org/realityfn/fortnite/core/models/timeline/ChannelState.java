package org.realityfn.fortnite.core.models.timeline;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ChannelState {
    @JsonProperty("validFrom")
    public String ValidFrom;

    @JsonProperty("activeEvents")
    public ArrayList<ChannelEvent> ActiveEvents;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("state")
    public State State = new State();

    public ChannelState(ArrayList<ChannelEvent> activeEvents, State state) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(java.time.ZoneId.of("UTC"));
        ValidFrom = dtf.format(java.time.Instant.now());
        ActiveEvents = activeEvents;
        State = state;
    }
}
