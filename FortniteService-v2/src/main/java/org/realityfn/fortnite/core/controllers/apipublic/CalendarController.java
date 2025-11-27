package org.realityfn.fortnite.core.controllers.apipublic;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.realityfn.common.annotations.OAuth;
import org.realityfn.common.enums.Actions;
import org.realityfn.fortnite.core.config.BuildProperties;
import org.realityfn.fortnite.core.models.timeline.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@Path("/api/calendar/v1")
@Produces(MediaType.APPLICATION_JSON)
public class CalendarController {

    @GET
    @Path("/timeline")
    @OAuth(resource = "calendar", action = Actions.READ)
    public Response timeline() {
        int currentSeason = BuildProperties.getCurrentSeason();
        String lobby = "LobbySeason" + currentSeason;

        LinkedHashMap<String, TimelineChannel> channels = new LinkedHashMap<String, TimelineChannel>() {
            {
                put("standalone-store", new TimelineChannel(new ArrayList<ChannelState>(){
                    {
                        add(new ChannelState(new ArrayList<ChannelEvent>(), new State(true)));
                    }
                }));
                put("client-matchmaking", new TimelineChannel(new ArrayList<ChannelState>()));
                put("client-events", new TimelineChannel(new ArrayList<ChannelState>(){
                    {
                        add(new ChannelState(new ArrayList<ChannelEvent>() {
                            {
                                add(new ChannelEvent("EventFlag.Season" + currentSeason));
                                add(new ChannelEvent("EventFlag." + lobby));
                            }
                        }, new State(currentSeason)));
                    }
                }));
            }
        };

        return Response.ok(new Timeline(channels)).build();
    }
}