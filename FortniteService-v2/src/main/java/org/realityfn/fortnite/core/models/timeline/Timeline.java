package org.realityfn.fortnite.core.models.timeline;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;

public class Timeline {
    @JsonProperty("channels")
    public LinkedHashMap<String, TimelineChannel> Channels;

    @JsonProperty("currentTime")
    public String CurrentTime;

    @JsonProperty("eventsTimeOffsetHrs")
    public int EventsTimeOffsetHrs = 0;

    @JsonProperty("cacheIntervalMins")
    public double CacheIntervalMins = 15.0;

    public Timeline(LinkedHashMap<String, TimelineChannel> channels) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("UTC"));

        this.Channels = channels;
        this.CurrentTime = dtf.format(java.time.Instant.now());
    }
}
