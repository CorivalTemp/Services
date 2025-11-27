package org.realityfn.fortnite.core.utils.repositories.fortnite;

import org.realityfn.fortnite.core.models.Keychain;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeychainRepository extends MongoRepository<Keychain, String> {

}