package org.realityfn.common.interceptors;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyReader;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Priority(Priorities.ENTITY_CODER)
public class CustomJsonMessageBodyReader implements MessageBodyReader<Object> {

    private static final ObjectMapper mapper = new ObjectMapper()
            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
            .setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)
            .setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        // Only handle JSON media types
        return mediaType.isCompatible(MediaType.APPLICATION_JSON_TYPE);
    }

    @Override
    public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations,
                           MediaType mediaType, MultivaluedMap<String, String> httpHeaders,
                           InputStream entityStream) throws IOException, WebApplicationException {
        try {
            return mapper.readValue(entityStream, type);
        } catch (JsonMappingException e) {
            throw new JsonMappingCustomException(e);
        } catch (JsonProcessingException e) {
            throw new JsonParseCustomException(e);
        }
    }

    public static class JsonParseCustomException extends RuntimeException {
        private final JsonProcessingException jsonException;

        public JsonParseCustomException(JsonProcessingException cause) {
            super(cause.getMessage(), cause);
            this.jsonException = cause;
        }

        public JsonProcessingException getJsonException() {
            return jsonException;
        }
    }

    public static class JsonMappingCustomException extends RuntimeException {
        private final JsonMappingException jsonException;

        public JsonMappingCustomException(JsonMappingException cause) {
            super(cause.getMessage(), cause);
            this.jsonException = cause;
        }

        public JsonMappingException getJsonException() {
            return jsonException;
        }
    }
}