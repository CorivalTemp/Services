package org.realityfn.common.exceptions;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.realityfn.common.exceptions.common.MissingParamException;
import org.realityfn.common.exceptions.common.ServerErrorException;
import org.realityfn.common.exceptions.common.UnsupportedMediaTypeException;
import org.realityfn.common.interceptors.CustomJsonMessageBodyReader;
import org.realityfn.common.utils.AdminAccess;

import java.util.List;
import java.util.Objects;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class GlobalExceptionHandler implements ExceptionMapper<Throwable> {

    @Context
    private HttpHeaders httpHeaders;

    @Context
    private ContainerRequestContext requestContext;

    private static final ObjectMapper mapper = new ObjectMapper();


    @Override
    public Response toResponse(Throwable throwable) {

        if (throwable instanceof BaseException e) {
            return handleBaseException(e);
        }

        if (throwable instanceof CustomJsonMessageBodyReader.JsonParseCustomException jsonEx) {
            return handleJsonProcessingException(jsonEx.getJsonException());
        }

        if (throwable instanceof CustomJsonMessageBodyReader.JsonMappingCustomException mappingEx) {
            return handleJsonMappingException(mappingEx.getJsonException());
        }

        if (throwable instanceof jakarta.ws.rs.NotFoundException) {
            return handleNotFoundException();
        }
        if (throwable instanceof jakarta.ws.rs.NotAllowedException e) {
            return handleNotAllowedException(e);
        }
        if (throwable instanceof jakarta.ws.rs.NotSupportedException) {
            return handleUnsupportedMediaException();
        }

        if (throwable instanceof jakarta.ws.rs.ProcessingException pe) {
            Throwable cause = pe.getCause();
            while (cause != null) {
                if (cause instanceof JsonParseException jpe) {
                    return handleJsonParseException(jpe);
                }
                if (cause instanceof JsonMappingException jme) {
                    return handleJsonMappingException(jme);
                }
                if (cause instanceof JsonProcessingException jpe) {
                    return handleJsonProcessingException(jpe);
                }
                cause = cause.getCause();
            }
        }

        if (throwable instanceof jakarta.ws.rs.BadRequestException bre) {
            Throwable cause = bre.getCause();
            // Check the entire cause chain
            while (cause != null) {
                if (cause instanceof JsonParseException jpe) {
                    return handleJsonParseException(jpe);
                }
                if (cause instanceof JsonMappingException jme) {
                    return handleJsonMappingException(jme);
                }
                if (cause instanceof JsonProcessingException jpe) {
                    return handleJsonProcessingException(jpe);
                }
                cause = cause.getCause();
            }
            return handleGenericException(bre);
        }

        if (throwable instanceof JsonMappingException mappingEx) {
            return handleJsonMappingException(mappingEx);
        }
        if (throwable instanceof JsonParseException parseEx) {
            return handleJsonParseException(parseEx);
        }
        if (throwable instanceof JsonProcessingException processingEx) {
            return handleJsonProcessingException(processingEx);
        }

        if (throwable instanceof NullPointerException) {
            return handleNullTypeException();
        }
        throwable.printStackTrace();
        return handleGenericException(throwable);
    }

    private Response handleBaseException(BaseException e) {
        EpicErrorModel model = new EpicErrorModel(
                e.ErrorCode,
                e.getMessage(),
                e.MessageVars,
                e.NumericErrorCode,
                getOriginatingService()
        );

        if (e.additionalFields != null && !e.additionalFields.isEmpty()) {
            model.additionalFields = e.additionalFields;
        }

        if (e.violationFailed != null && !e.violationFailed.isEmpty()) {
            model.violationFailed = e.violationFailed;
        }
        return buildResponse(model, Response.Status.fromStatusCode(e.StatusCode));
    }

    private Response handleNotFoundException() {
        EpicErrorModel model = new EpicErrorModel(
                "errors.com.epicgames.common.not_found",
                "Sorry, the resource you were trying to find could not be found",
                1004,
                getOriginatingService()
        );
        return buildResponse(model, Response.Status.NOT_FOUND);
    }

    private Response handleNotAllowedException(jakarta.ws.rs.NotAllowedException e) {
        EpicErrorModel model = new EpicErrorModel(
                "errors.com.epicgames.common.method_not_allowed",
                "Sorry, the resource you were trying to access cannot be accessed with the HTTP method you used.",
                1009,
                getOriginatingService()
        );

        return buildResponse(model, Response.Status.METHOD_NOT_ALLOWED);
    }

    private Response handleUnsupportedMediaException() {
        UnsupportedMediaTypeException serverError = new UnsupportedMediaTypeException();
        EpicErrorModel model = new EpicErrorModel(
                serverError.ErrorCode,
                serverError.getMessage(),
                serverError.MessageVars,
                serverError.NumericErrorCode,
                getOriginatingService()
        );
        return buildResponse(model, Response.Status.UNSUPPORTED_MEDIA_TYPE);
    }

    private Response handleJsonMappingException(JsonMappingException ex) {
        List<String> fields = ex.getPath().stream()
                .map(JsonMappingException.Reference::getFieldName)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        EpicErrorModel model = new EpicErrorModel(
                "errors.com.epicgames.common.json_mapping_error",
                "Json mapping failed.",
                fields.toArray(String[]::new),
                1019,
                getOriginatingService()
        );

        return buildResponse(model, Response.Status.BAD_REQUEST);
    }

    private Response handleNullTypeException() {
        MissingParamException serverError = new MissingParamException();
        EpicErrorModel model = new EpicErrorModel(
                serverError.ErrorCode,
                serverError.getMessage(),
                serverError.MessageVars,
                serverError.NumericErrorCode,
                getOriginatingService()
        );
        return buildResponse(model, Response.Status.BAD_REQUEST);
    }

    private Response handleGenericException(Throwable e) {
        ServerErrorException serverError = new ServerErrorException((Exception) e);
        EpicErrorModel model = new EpicErrorModel(
                serverError.ErrorCode,
                serverError.getMessage(),
                serverError.MessageVars,
                serverError.NumericErrorCode,
                getOriginatingService()
        );
        return buildResponse(model, Response.Status.INTERNAL_SERVER_ERROR);
    }

    private Response handleJsonParseException(JsonParseException ex) {
        String position = "line: " + ex.getLocation().getLineNr() +
                "; column: " + ex.getLocation().getColumnNr();

        EpicErrorModel model = new EpicErrorModel(
                "errors.com.epicgames.common.json_parse_error",
                "Json parse failed.",
                new String[]{ position },
                1020,
                getOriginatingService()
        );

        return buildResponse(model, Response.Status.BAD_REQUEST);
    }

    private Response handleJsonProcessingException(JsonProcessingException ex) {
        // Extract location information if available
        String[] messageVars = new String[]{};
        if (ex.getLocation() != null) {
            String position = "line: " + ex.getLocation().getLineNr() +
                    "; column: " + ex.getLocation().getColumnNr();
            messageVars = new String[]{ position };
        }

        EpicErrorModel model = new EpicErrorModel(
                "errors.com.epicgames.common.json_parse_error",
                "Json parse failed.",
                messageVars,
                1020,
                getOriginatingService()
        );

        return buildResponse(model, Response.Status.BAD_REQUEST);
    }

    private Response buildResponse(EpicErrorModel model, Response.Status status) {
        return Response.status(status)
                .header("X-Epic-Error-Name", model.ErrorCode)
                .header("X-Epic-Error-Code", String.valueOf(model.NumericErrorCode))
                .type(MediaType.APPLICATION_JSON)
                .entity(model)
                .build();
    }

    public String getOriginatingService() {
        try {
            return AdminAccess.fetchCurrentAppName(requestContext);
        } catch (Exception e) {
            return "unknown";
        }
    }
}