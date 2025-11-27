package org.realityfn.fortnite.core.utils.repositories.fortnite;

import org.realityfn.fortnite.core.models.cloudstorage.ConfigDataEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SystemCloudstorageRepository extends MongoRepository<ConfigDataEntry, String> {
    Optional<ConfigDataEntry> findByUniqueFileName(String uniqueFileName);
    Optional<ConfigDataEntry> findByFileName(String fileName);
    Optional<ConfigDataEntry>[] findByHidden(boolean hidden);
    void deleteByUniqueFileName(String uniqueFileName);
}
