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
import org.realityfn.fortnite.core.utils.repositories.fortnite.SystemCloudstorageRepository;
import org.realityfn.fortnite.core.utils.BrotliUtils;

import java.io.IOException;
import java.time.Instant;
import java.util.*;

@Path("/api/cloudstorage")
public class SystemCloudstorageController {

    @Context
    private UriInfo uriInfo;

    private final SystemCloudstorageRepository systemCloudstorageRepository;

    @Inject
    public SystemCloudstorageController(SystemCloudstorageRepository systemCloudstorageRepository) {
        this.systemCloudstorageRepository = systemCloudstorageRepository;
    }


    @GET
    @Path("/system/config")
    @Produces(MediaType.APPLICATION_JSON)
    @OAuth(resource = "cloudstorage:system", action = Actions.READ)
    public Config config() {
        return new Config();
    }

    @GET
    @Path("/system/{uniqueFileName}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @OAuth(resource = "cloudstorage:system:{uniqueFileName}", action = Actions.READ)
    public Response systemGet(@PathParam("uniqueFileName") String uniqueFileName) throws CloudstorageFileNotFoundException, IOException {

        Optional<ConfigDataEntry> configFile = systemCloudstorageRepository.findByUniqueFileName(uniqueFileName);

        if (configFile.isEmpty()) throw new CloudstorageFileNotFoundException(uniqueFileName);
        if (Objects.equals(configFile.get().getCompressionMethod(), "brotli")) {
            byte[] dataInfo = BrotliUtils.decompressFromBase64(configFile.get().getData()).getBytes();
            return Response.ok(dataInfo).build();

        }
        return Response.noContent().build();
    }

    @POST
    @Path("/system")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @OAuth(resource = "cloudstorage:system", action = Actions.CREATE)
    public Response systemUpload(@PathParam("filename") String filename, byte[] fileData)
            throws CloudstorageSystemFileAlreadyExistsException, ServerErrorException {

        String uniqueFileName = java.util.UUID.randomUUID().toString().replace("-", "");

        Optional<ConfigDataEntry> configFile = systemCloudstorageRepository.findByFileName(filename);

        if (configFile.isPresent()) {
            throw new CloudstorageSystemFileAlreadyExistsException();
        }

        try {
            String encodedFileData = BrotliUtils.compressToBase64(fileData);
            systemCloudstorageRepository.save(new ConfigDataEntry(uniqueFileName, filename, encodedFileData, "brotli", false, Instant.now()));
            return Response.ok(uniqueFileName).build();
        } catch (IOException e) {
            System.err.println("Error uploading file: " + e.getMessage());
            throw new ServerErrorException();
        }
    }
    @GET
    @Path("/system")
    @Produces(MediaType.APPLICATION_JSON)
    @OAuth(resource = "cloudstorage:system", action = Actions.READ)
    public Response systemList() throws CloudstorageFileNotFoundException {
        Optional<ConfigDataEntry>[] nonHiddenFiles = systemCloudstorageRepository.findByHidden(false);
        CloudStorageFile[] files = new CloudStorageFile[nonHiddenFiles.length];
        for (int i = 0; i < nonHiddenFiles.length; i++) {
            files[i] = new CloudStorageFile(nonHiddenFiles[i].get());
        }
        return Response.ok(files).build();
    }

    @PUT
    @Path("/system/{uniqueFileName}")
    @OAuth(resource = "cloudstorage:system:{uniqueFileName}", action = Actions.UPDATE)
    public Response systemUpdate(@PathParam("uniqueFileName") String uniqueFileName, byte[] fileData)
            throws CloudstorageFileNotFoundException, ServerErrorException {

        Optional<ConfigDataEntry> configFile = systemCloudstorageRepository.findByUniqueFileName(uniqueFileName);
        if (configFile.isEmpty()) {
            throw new CloudstorageFileNotFoundException(uniqueFileName);
        }

        try {
            String encodedFileData = BrotliUtils.compressToBase64(fileData);
            ConfigDataEntry existingEntry = configFile.get();
            existingEntry.setData(encodedFileData);
            systemCloudstorageRepository.save(existingEntry);
            return Response.noContent().build();
        } catch (IOException e) {
            System.err.println("Error updating file: " + e.getMessage());
            throw new ServerErrorException();
        }
    }

    @DELETE
    @Path("/system/{uniqueFileName}")
    @OAuth(resource = "cloudstorage:system:{uniqueFileName}", action = Actions.DELETE)
    public Response systemDelete(@PathParam("uniqueFileName") String uniqueFileName)
            throws CloudstorageFileNotFoundException, ServerErrorException {

        Optional<ConfigDataEntry> configFile = systemCloudstorageRepository.findByUniqueFileName(uniqueFileName);
        if (configFile.isEmpty()) {
            throw new CloudstorageFileNotFoundException(uniqueFileName);
        }

        systemCloudstorageRepository.deleteByUniqueFileName(uniqueFileName);
        return Response.noContent().build();
    }
}
