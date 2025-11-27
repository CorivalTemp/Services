package org.realityfn.fortnite.core.utils.repositories.fortnite;

import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.realityfn.common.exceptions.common.MongoExecutionTimeoutError;
import org.realityfn.common.utils.database.setup.BaseDAOImpl;
import org.realityfn.fortnite.core.config.BuildProperties;
import org.realityfn.fortnite.core.models.fortnite.matchmakingservice.MatchmakingConfigDataEntry;

public class MatchmakingConfigRepository extends BaseDAOImpl<MatchmakingConfigDataEntry> {

    public MatchmakingConfigRepository() {
        super("matchmaking_config", MatchmakingConfigDataEntry.class);
    }

    public MatchmakingConfigDataEntry findConfig() {
        try {
            Document doc = collection.find(
                    Filters.eq("_id", BuildProperties.APP)).first();

            if (doc != null)
                return mapper.readValue(doc.toJson(), MatchmakingConfigDataEntry.class);

            return new MatchmakingConfigDataEntry();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MongoExecutionTimeoutError();
        }
    }
}