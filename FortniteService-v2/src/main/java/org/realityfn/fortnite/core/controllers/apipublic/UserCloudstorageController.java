package org.realityfn.fortnite.core.controllers.apipublic;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.core.MediaType;
import org.realityfn.common.annotations.OAuth;
import org.realityfn.common.enums.Actions;
import org.realityfn.fortnite.core.exceptions.cloudstorage.CloudstorageFileNotFoundException;
import org.realityfn.fortnite.core.exceptions.cloudstorage.CloudstorageSystemFileAlreadyExistsException;
import org.realityfn.common.exceptions.common.ServerErrorException;
import org.realityfn.fortnite.core.models.cloudstorage.CloudStorageFile;
import org.realityfn.fortnite.core.models.cloudstorage.Config;
import org.realityfn.fortnite.core.models.cloudstorage.ConfigDataEntry;
import org.realityfn.fortnite.core.models.cloudstorage.UserCloudstorageDataEntry;
import org.realityfn.fortnite.core.utils.repositories.fortnite.UserCloudstorageRepository;
import org.realityfn.fortnite.core.utils.BrotliUtils;

import java.io.IOException;
import java.time.Instant;
import java.util.*;

@Path("/api/cloudstorage")
public class UserCloudstorageController {

    @Context
    private UriInfo uriInfo;

    private final UserCloudstorageRepository userCloudstorageRepository;

    @Inject
    public UserCloudstorageController(UserCloudstorageRepository userCloudstorageRepository) {
        this.userCloudstorageRepository = userCloudstorageRepository;
    }

    @GET
    @Path("/user/{accountId}/config")
    @Produces(MediaType.APPLICATION_JSON)
    @OAuth(resource = "cloudstorage:user", action = Actions.READ)
    public Response config(@PathParam("accountId") String accountId) {
        return Response.ok(new Config()).build();
    }

    @GET
    @Path("/user/{accountId}/{uniqueFileName}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @OAuth(resource = "cloudstorage:user:{accountId}:{uniqueFileName}", action = Actions.READ)
    public Response userGet(@PathParam("accountId") String accountId,
                            @PathParam("uniqueFileName") String uniqueFileName)
            throws IOException, ServerErrorException {

        UserCloudstorageDataEntry userSettings = userCloudstorageRepository.findByAccountId(accountId)
                .orElseThrow(() -> new CloudstorageFileNotFoundException(uniqueFileName, accountId));

        ConfigDataEntry fileEntry = userSettings.getCloudstorageFiles().stream()
                .filter(file -> uniqueFileName.equals(file.getUniqueFileName()))
                .findFirst()
                .orElseThrow(() -> new CloudstorageFileNotFoundException(uniqueFileName, accountId));

        byte[] decompressedData = switch (fileEntry.getCompressionMethod()) {
            case "brotli" -> BrotliUtils.decompressFromBase64(fileEntry.getData()).getBytes();
            default -> throw new ServerErrorException();
        };

        return Response.ok(decompressedData).build();
    }

    @POST
    @Path("/user/{accountId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @OAuth(resource = "cloudstorage:user:{accountId}", action = Actions.CREATE)
    public Response userUpload(@PathParam("accountId") String accountId,
                               @QueryParam("filename") String filename,
                               byte[] fileData)
            throws CloudstorageSystemFileAlreadyExistsException, ServerErrorException, IOException {

        Optional<UserCloudstorageDataEntry> userSettings = userCloudstorageRepository.findByAccountId(accountId);

        if (userSettings.isEmpty()) {
            userCloudstorageRepository.save(
                    new UserCloudstorageDataEntry(
                            accountId,
                            new ArrayList<>(List.of(
                                    new ConfigDataEntry(filename, filename, BrotliUtils.compressToBase64(fileData), "brotli", false, Instant.now())
                            ))
                    )
            );
            return Response.ok(filename).build();
        }

        boolean fileEntryExists = userSettings.get().getCloudstorageFiles().stream()
                .anyMatch(file -> filename.equals(file.getFileName()));

        if (fileEntryExists) {
            throw new CloudstorageSystemFileAlreadyExistsException();
        }

        UserCloudstorageDataEntry existingData = userSettings.get();
        existingData.addFile(
                new ConfigDataEntry(filename, filename, BrotliUtils.compressToBase64(fileData), "brotli", false, Instant.now())
        );
        userCloudstorageRepository.save(existingData);

        return Response.ok(filename).build();
    }

    @GET
    @Path("/user/{accountId}")
    @Produces(MediaType.APPLICATION_JSON)
    @OAuth(resource = "cloudstorage:user:{accountId}", action = Actions.READ)
    public Response userList(@PathParam("accountId") String accountId) {
        Optional<UserCloudstorageDataEntry> userSettings = userCloudstorageRepository.findByAccountId(accountId);

        if (userSettings.isEmpty()) {
            return Response.ok(new CloudStorageFile[0]).build();
        }

        List<ConfigDataEntry> settingsFiles = userSettings.get().getCloudstorageFiles();
        CloudStorageFile[] files = new CloudStorageFile[settingsFiles.size()];

        for (int i = 0; i < settingsFiles.size(); i++) {
            files[i] = new CloudStorageFile(settingsFiles.get(i), accountId);
        }

        return Response.ok(files).build();
    }

    @PUT
    @Path("/user/{accountId}/{uniqueFileName}")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @OAuth(resource = "cloudstorage:user:{accountId}:{uniqueFileName}", action = Actions.UPDATE)
    public Response userUpdate(@PathParam("accountId") String accountId,
                               @PathParam("uniqueFileName") String uniqueFileName,
                               byte[] fileData)
            throws IOException, ServerErrorException {

        Optional<UserCloudstorageDataEntry> userSettings = userCloudstorageRepository.findByAccountId(accountId);

        if (userSettings.isEmpty()) {
            userCloudstorageRepository.save(
                    new UserCloudstorageDataEntry(
                            accountId,
                            new ArrayList<>(List.of(
                                    new ConfigDataEntry(uniqueFileName, uniqueFileName, BrotliUtils.compressToBase64(fileData), "brotli", false, Instant.now())
                            ))
                    )
            );
            return Response.ok(uniqueFileName).build();
        }

        try {
            String encodedFileData = BrotliUtils.compressToBase64(fileData);
            UserCloudstorageDataEntry existingEntry = userSettings.get();

            if (!existingEntry.editFile(uniqueFileName, encodedFileData)) {
                existingEntry.addFile(
                        new ConfigDataEntry(uniqueFileName, uniqueFileName, encodedFileData, "brotli", false, Instant.now())
                );
            }

            userCloudstorageRepository.save(existingEntry);
            return Response.noContent().build();
        } catch (IOException e) {
            System.err.println("Error updating file: " + e.getMessage());
            throw new ServerErrorException();
        }
    }

    @DELETE
    @Path("/user/{accountId}/{uniqueFileName}")
    @OAuth(resource = "cloudstorage:user:{accountId}:{uniqueFileName}", action = Actions.DELETE)
    public Response userDelete(@PathParam("accountId") String accountId,
                               @PathParam("uniqueFileName") String uniqueFileName) {

        UserCloudstorageDataEntry userSettings = userCloudstorageRepository.findByAccountId(accountId)
                .orElseThrow(() -> new CloudstorageFileNotFoundException(uniqueFileName, accountId));

        ConfigDataEntry fileEntry = userSettings.getCloudstorageFiles().stream()
                .filter(file -> uniqueFileName.equals(file.getUniqueFileName()))
                .findFirst()
                .orElseThrow(() -> new CloudstorageFileNotFoundException(uniqueFileName, accountId));

        userSettings.removeFile(uniqueFileName);
        userCloudstorageRepository.save(userSettings);

        return Response.noContent().build();
    }
}