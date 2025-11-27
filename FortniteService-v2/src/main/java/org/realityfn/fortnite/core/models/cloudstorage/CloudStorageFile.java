package org.realityfn.fortnite.core.models.cloudstorage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.realityfn.fortnite.core.exceptions.cloudstorage.CloudstorageFileNotFoundException;
import org.realityfn.fortnite.core.managers.CryptoManager;
import org.realityfn.fortnite.core.utils.BrotliUtils;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CloudStorageFile {
    public String uniqueFilename;
    public String filename;
    public String hash;
    public String hash256;
    public long length;
    public String contentType;
    public String uploaded;
    public String storageType;
    public Map<String, String> storageIds; // Only use this if storageType is DSS, so far since we use MONGO it's unneeded.
    public boolean doNotCache;
    public String accountId;

    public CloudStorageFile(ConfigDataEntry cloudstorageFile) throws CloudstorageFileNotFoundException {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("UTC"));

            uniqueFilename = cloudstorageFile.getUniqueFileName();
            filename = cloudstorageFile.getFileName();
            byte[] data = BrotliUtils.decompressFromBase64(cloudstorageFile.getData()).getBytes();
            length = data.length;

            hash = CryptoManager.hashSha1(data);
            hash256 = CryptoManager.hashSha256(data);
            contentType = "application/octet-stream";
            storageType = "MONGO";
            uploaded = dtf.format(cloudstorageFile.getUploaded());
            doNotCache = false;

        } catch (Exception e) {
            throw new CloudstorageFileNotFoundException(uniqueFilename);
        }
    }
    public CloudStorageFile(ConfigDataEntry cloudstorageFile, String userAccountId) throws CloudstorageFileNotFoundException {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("UTC"));

            uniqueFilename = cloudstorageFile.getUniqueFileName();
            filename = cloudstorageFile.getFileName();
            byte[] data = BrotliUtils.decompressFromBase64(cloudstorageFile.getData()).getBytes();
            length = data.length;

            hash = CryptoManager.hashSha1(data);
            hash256 = CryptoManager.hashSha256(data);
            contentType = "application/octet-stream";
            storageType = "MONGO";
            uploaded = dtf.format(cloudstorageFile.getUploaded());
            doNotCache = false;
            accountId = userAccountId;

        } catch (Exception e) {
            throw new CloudstorageFileNotFoundException(uniqueFilename);
        }
    }
}
