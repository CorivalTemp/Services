package org.realityfn.fortnite.core.utils.repositories.fortnite;

import com.mongodb.client.model.Filters;
import jakarta.inject.Singleton;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.realityfn.common.exceptions.common.MongoExecutionTimeoutError;
import org.realityfn.common.utils.database.setup.BaseDAOImpl;
import org.realityfn.fortnite.core.config.BuildProperties;
import org.realityfn.fortnite.core.models.gamesessions.GameSessionDataEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GameSessionRepository extends BaseDAOImpl<GameSessionDataEntry> {

    public GameSessionRepository() {
        super(BuildProperties.APP + "_game_sessions", GameSessionDataEntry.class);
    }

    public Optional<GameSessionDataEntry> findSessionById(String sessionId) {
        try {
            Document doc = collection.find(
                    Filters.eq("_id", sessionId)).first();

            if (doc != null)
                return Optional.of(mapper.readValue(doc.toJson(), GameSessionDataEntry.class));

            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MongoExecutionTimeoutError();
        }
    }

    public void createOrUpdateSession(GameSessionDataEntry sessionInfo) {
        try {
            if (findSessionById(sessionInfo.getId()).isEmpty()) {
                save(sessionInfo);
            } else {
                update(sessionInfo);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public List<GameSessionDataEntry> findAllSessionsByAccountId(String accountId) {
        try {

            return collection.find(
                    Filters.eq("publicPlayers", accountId)).map(doc -> {
                        try {
                            return mapper.readValue(doc.toJson(), GameSessionDataEntry.class);
                        } catch (Exception e) {
                            throw new RuntimeException("Error mapping document", e);
                        }
                    })
                    .into(new ArrayList<>());
        } catch (Exception e) {
            e.printStackTrace();
            throw new MongoExecutionTimeoutError();
        }
    }

    public GameSessionDataEntry update(GameSessionDataEntry sessionInfo) {
        String sessionId = sessionInfo.getId();
        return update(sessionId, sessionInfo);
    }

    @Override
    public GameSessionDataEntry save(GameSessionDataEntry sessionInfo) {
        try {
            Document doc = Document.parse(mapper.writeValueAsString(sessionInfo));
            if (!doc.containsKey("_id")) doc.put("_id", sessionInfo.getId());
            collection.insertOne(doc);
            return mapper.readValue(doc.toJson(), entityClass);
        } catch (Exception e) {
            throw new RuntimeException("Error saving entity", e);
        }
    }
}