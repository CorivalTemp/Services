package org.realityfn.fortnite.core.utils.repositories.lightswitch;

import org.realityfn.fortnite.core.models.mongo.LightswitchDataEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LightswitchRepository extends MongoRepository<LightswitchDataEntry, String> {
}