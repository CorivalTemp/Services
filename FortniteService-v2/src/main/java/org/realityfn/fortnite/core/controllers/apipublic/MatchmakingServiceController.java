package org.realityfn.fortnite.core.controllers.apipublic;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.realityfn.common.annotations.OAuth;
import org.realityfn.common.enums.Actions;
import org.realityfn.common.exceptions.common.ServerErrorException;
import org.realityfn.fortnite.core.enums.RegionType;
import org.realityfn.fortnite.core.exceptions.fortnite.*;
import org.realityfn.fortnite.core.managers.profiles.ProfileManager;
import org.realityfn.fortnite.core.managers.profiles.TemplateManager;
import org.realityfn.fortnite.core.models.fortnite.matchmakingservice.MatchmakingConfigDataEntry;
import org.realityfn.fortnite.core.models.fortnite.matchmakingservice.MatchmakingTicketPayload;
import org.realityfn.fortnite.core.models.fortnite.matchmakingservice.MatchmakingTicketResponse;
import org.realityfn.fortnite.core.models.profiles.Profile;
import org.realityfn.fortnite.core.models.profiles.attributes.BanStatus;
import org.realityfn.fortnite.core.utils.repositories.fortnite.MatchmakingConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

@Path("/api/game/v2/matchmakingservice/ticket")
@Produces(MediaType.APPLICATION_JSON)
public class MatchmakingServiceController {

    private ObjectMapper mapper = new ObjectMapper();

    @Context
    private ContainerRequestContext requestContext;

    private final MatchmakingConfigRepository matchmakingConfigRepository;

    private static Logger TicketLogger = LoggerFactory.getLogger(MatchmakingServiceController.class);

    @Inject
    public MatchmakingServiceController(MatchmakingConfigRepository matchmakingConfigRepository) {
        this.matchmakingConfigRepository = matchmakingConfigRepository;
    }

    @GET
    @Path("/player/{playerId}")
    @OAuth(resource = "profile:{playerId}:commands", action = Actions.CREATE)
    public Response matchmakingTicketPlayer(
            @HeaderParam("User-Agent") String userAgent,
            @PathParam("playerId")  String playerId,
            @QueryParam("partyPlayerIds") @DefaultValue("") String partyPlayerIdsString,
            @QueryParam("bucketId") @DefaultValue("") String bucketId,
            @QueryParam("player.platform") String platform,
            @QueryParam("player.subregions") @DefaultValue("") String playerSubregions,
            @QueryParam("player.option.enableModeratorMode") boolean enableModeratorMode,
            @QueryParam("player.option.customKey") @DefaultValue("") String customKey
    ) {

        ProfileManager profileManager = ProfileManager.getInstance();

        // Example User-Agent: Fortnite/++Fortnite+Release-32.10-CL-37958378 Windows/10.0.19045.1.768.64bit

        String userAgentRegex = "(.*)/(.*)-CL-(\\d+)(\\s+\\((.*?)\\))?\\s+(\\w+)/(\\S*)(\\s*\\((.*?)\\))?";
        //String userAgentRegex = "(.*)/(.*-CL-\\d+)\\s+(\\w+)/(\\S+)(?:\\s*\\((.*?)\\))?";

        if (!Pattern.compile(userAgentRegex).matcher(userAgent).find()) {
            throw new InvalidUserAgentException(userAgent, userAgentRegex);
        }

        TicketLogger.info("Player {} is matchmaking with User-Agent: {}", playerId, userAgent);

        MatchmakingConfigDataEntry config = matchmakingConfigRepository.findConfig();

        // Holy placeholder (replace with proper platform validation later)
        if (!platform.equals("Windows")) {
            throw new InvalidPlatformException(platform);
        }

        // Some checks for misc
        if (config.isMatchmakingDisabled()) {
            throw new PlaylistUnavailableException("Matchmaking is currently disabled.");
        }

        if (!customKey.isBlank() && (customKey.length() < 4 || customKey.length() > 16)) {
            throw new InvalidCustomKeyException(4, 16);
        }

        if (enableModeratorMode) {
            throw new ModeratorModeNotAllowed(playerId);
        }

        if (partyPlayerIdsString == null || partyPlayerIdsString.isBlank()) {
            throw new InvalidBucketIdException("partyPlayerIds is blank");
        }

        List<String> partyPlayerIds = Arrays.asList(partyPlayerIdsString.split(","));

        if (!partyPlayerIds.contains(playerId)) {
            throw new ServerErrorException();
        }

        // Parse bucketId
        if (bucketId == null || bucketId.isBlank()) {
            throw new InvalidBucketIdException("bucketId is blank");
        }

        if (!Pattern.compile("^\\d+:\\d+:.+:.+").matcher(bucketId).find()) {
            throw new InvalidBucketIdException("Failed to parse bucketId: '{}'", bucketId);
        }

        // BucketID Format: Namespace:Deployment:NetCL:PlaylistHotfixVersion:Region:PlaylistID:CrossplayPool:Publicity:SplitId
        // Example: FN:Live:13920814:1:EU:Playlist_ItemTest_NoBuild:PC:public:1
        String fullBucketId = "FN:Live:" + bucketId + ":PC:public:1";

        String[] bucketParts = bucketId.split(":");
        record BucketInfo(String netCl, String hotfixVersion, String region, String playlistId) {}

        BucketInfo bucketInfo = new BucketInfo(bucketParts[0], bucketParts[1], bucketParts[2], bucketParts[3]);

        RegionType region = RegionType.fromRegionId(bucketInfo.region);

        TicketLogger.info("Bucket Info: Network Build: {}, Playlist Hotfix Version: {}, Region: {}, Playlist ID: {}, Full BucketID: {}", bucketInfo.netCl, bucketInfo.hotfixVersion, bucketInfo.region, bucketInfo.playlistId, fullBucketId);

        if (region == null) {
            throw new InvalidRegionException(bucketInfo.region);
        }

        if (TemplateManager.getTemplate("PlaylistData:" + bucketInfo.playlistId.toLowerCase()) == null) {
            throw new InvalidPlaylistException(bucketInfo.playlistId);
        }

        if (config.getBlacklistedBucketIds().contains(bucketInfo.netCl)) {
            TicketLogger.info("Network version '{}' is not currently allowed to matchmake", bucketInfo.netCl);
            throw new PlaylistUnavailableException("Network version '{}' is not currently allowed to matchmake", bucketInfo.netCl);
        }


        if (config.getBlacklistedPlaylistIds().contains(bucketInfo.playlistId.toLowerCase())) {
            throw new PlaylistUnavailableException("Playlist '{}' is not currently allowed to matchmake.", bucketInfo.playlistId);
        }

        // Check if user is banned
        Profile profile = profileManager.getProfile(playerId, "common_core");
        if (profile == null) {
            throw new ProfileRequiredException(playerId);
        }


        BanStatus banStatus = mapper.convertValue(profile.getStat("ban_status"), BanStatus.class);

        if (!(banStatus == null)) {
            Instant banExpiry = Instant.parse(banStatus.getBanStartTimeUtc()).plusSeconds((long) (banStatus.getBanDurationDays() * 24 * 60 * 60));
            if (Instant.now().isBefore(banExpiry)) {
                throw new PlayerBannedFromSubgameException(Math.round(banStatus.getBanDurationDays()));
            }
        }

        String preferredSubregion = "None";
        if (!(playerSubregions == null)) {
            preferredSubregion = playerSubregions.split(",")[0];
        }


        HashMap<String, String> ticketAttributes = new HashMap<>();
        ticketAttributes.put("player.userAgent", userAgent);
        ticketAttributes.put("player.platform", platform);
        ticketAttributes.put("player.preferredSubregion", preferredSubregion);
        ticketAttributes.put("player.option.spectator", "false");
        ticketAttributes.put("player.inputTypes", "");
        ticketAttributes.put("playlist.revision", bucketInfo.hotfixVersion);

        MatchmakingTicketPayload payloadTicket = new MatchmakingTicketPayload(
                playerId,
                partyPlayerIds,
                fullBucketId,
                ticketAttributes,
                Instant.now().plusSeconds(30).toString()
        );

        MatchmakingTicketResponse response = new MatchmakingTicketResponse(
                region.getRegionHost(),
                "mms-player",
                Base64.getEncoder().encodeToString(payloadTicket.toString().getBytes())
        );

        return Response.ok(response).build();
    }
}
