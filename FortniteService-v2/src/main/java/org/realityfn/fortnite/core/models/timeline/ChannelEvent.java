package org.realityfn.fortnite.core.models.timeline;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.format.DateTimeFormatter;

public class ChannelEvent {
    @JsonProperty("eventType")
    public String EventType;

    @JsonProperty("activeUntil")
    public String ActiveUntil = "9999-01-01T00:00:00.000Z";

    @JsonProperty("activeSince")
    public String ActiveSince;

    public ChannelEvent(String eventType) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(java.time.ZoneId.of("UTC"));
        EventType = eventType;
        ActiveSince = dtf.format(java.time.Instant.now());
    }
}
