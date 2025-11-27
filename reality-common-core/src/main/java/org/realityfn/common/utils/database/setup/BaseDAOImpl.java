package org.realityfn.common.utils.database.setup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BaseDAOImpl<T> implements BaseDAO<T> {
    protected MongoCollection<Document> collection;
    protected Class<T> entityClass;
    protected ObjectMapper mapper = new ObjectMapper();

    public BaseDAOImpl(String collectionName, Class<T> entityClass) {
        this.collection = MongoDBConnection.getDatabase().getCollection(collectionName);
        this.entityClass = entityClass;
    }

    @Override
    public T save(T entity) {
        try {
            Document doc = Document.parse(mapper.writeValueAsString(entity));
            if (!doc.containsKey("_id")) doc.put("_id", new ObjectId());
            collection.insertOne(doc);
            return mapper.readValue(doc.toJson(), entityClass);
        } catch (Exception e) {
            throw new RuntimeException("Error saving entity", e);
        }
    }

    @Override
    public Optional<T> findById(String id) {
        try {
            Document doc = collection.find(Filters.eq("_id", new ObjectId(id))).first();
            if (doc != null) {
                return Optional.of(mapper.readValue(doc.toJson(), entityClass));
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException("Error finding entity by id", e);
        }
    }

    @Override
    public List<T> findAll() {
        try {
            return collection.find()
                    .map(doc -> {
                        try {
                            return mapper.readValue(doc.toJson(), entityClass);
                        } catch (Exception e) {
                            throw new RuntimeException("Error mapping document", e);
                        }
                    })
                    .into(new ArrayList<>());
        } catch (Exception e) {
            throw new RuntimeException("Error finding all entities", e);
        }
    }

    @Override
    public T update(String id, T entity) {
        try {
            Document doc = Document.parse(mapper.writeValueAsString(entity));
            collection.replaceOne(
                    Filters.eq("_id", id),
                    doc,
                    new ReplaceOptions().upsert(true)
            );

            return mapper.readValue(doc.toJson(), entityClass);
        } catch (Exception e) {
            throw new RuntimeException("Error updating entity", e);
        }
    }

    @Override
    public boolean delete(String id) {
        try {
            return collection.deleteOne(Filters.eq("_id", new ObjectId(id)))
                    .getDeletedCount() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Error deleting entity", e);
        }
    }

    @Override
    public List<T> findByField(String fieldName, Object value) {
        try {
            return collection.find(Filters.eq(fieldName, value))
                    .map(doc -> {
                        try {
                            return mapper.readValue(doc.toJson(), entityClass);
                        } catch (Exception e) {
                            throw new RuntimeException("Error mapping document", e);
                        }
                    })
                    .into(new ArrayList<>());
        } catch (Exception e) {
            throw new RuntimeException("Error finding entities by field", e);
        }
    }
}
