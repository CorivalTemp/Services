package org.realityfn.fortnite.core.utils.repositories.fortnite;

import org.realityfn.fortnite.core.models.cloudstorage.UserCloudstorageDataEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserCloudstorageRepository extends MongoRepository<UserCloudstorageDataEntry, String> {
    Optional<UserCloudstorageDataEntry> findByAccountId(String accountId);
}
