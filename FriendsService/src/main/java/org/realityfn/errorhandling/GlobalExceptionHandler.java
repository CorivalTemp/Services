package org.realityfn.errorhandling;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.realityfn.errorhandling.exceptions.common.NotFoundException;
import org.realityfn.errorhandling.exceptions.common.ServerErrorException;

// I'm killing myself
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class GlobalExceptionHandler implements ExceptionMapper<Throwable> {
    private static final String ORIGINATING_SERVICE = "friends";

    @Override
    public Response toResponse(Throwable throwable) {
        if (throwable instanceof BaseException) {
            return handleBaseException((BaseException) throwable);
        }

        if (throwable instanceof jakarta.ws.rs.NotFoundException) {
            return handleNotFoundException();
        }

        if (throwable instanceof jakarta.ws.rs.NotAllowedException) {
            return handleNotAllowedException();
        }

        return handleGenericException(throwable);
    }

    private Response handleBaseException(BaseException e) {
        return Response.status(e.StatusCode)
                .header("X-Epic-Error-Name", e.ErrorCode)
                .header("X-Epic-Error-Code", Integer.toString(e.StatusCode))
                .type(MediaType.APPLICATION_JSON)
                .entity(new EpicErrorModel(e.ErrorCode, e.getMessage(), e.MessageVars,
                        e.NumericErrorCode, ORIGINATING_SERVICE))
                .build();
    }

    private Response handleNotFoundException() {
        NotFoundException notFound = new NotFoundException();
        return Response.status(notFound.StatusCode)
                .header("X-Epic-Error-Name", "errors.com.epicgames.common.not_found")
                .header("X-Epic-Error-Code", "Sorry the resource you were trying to find could not be found")
                .type(MediaType.APPLICATION_JSON)
                .entity(new EpicErrorModel("errors.com.epicgames.common.not_found", "Sorry the resource you were trying to find could not be found",
                        1004, ORIGINATING_SERVICE))
                .build();
    }
    private Response handleNotAllowedException() {
        NotFoundException notFound = new NotFoundException();
        return Response.status(notFound.StatusCode)
                .header("X-Epic-Error-Name", notFound.ErrorCode)
                .header("X-Epic-Error-Code", Integer.toString(notFound.StatusCode))
                .type(MediaType.APPLICATION_JSON)
                .entity(new EpicErrorModel("errors.com.epicgames.common.method_not_allowed", "Sorry the resource you were trying to access cannot be accessed with the HTTP method you used.", 1009, ORIGINATING_SERVICE))
                .build();
    }
    private Response handleGenericException(Throwable e) {
        ServerErrorException serverError = new ServerErrorException((Exception) e);
        return Response.status(serverError.StatusCode)
                .header("X-Epic-Error-Name", serverError.ErrorCode)
                .header("X-Epic-Error-Code", Integer.toString(serverError.StatusCode))
                .type(MediaType.APPLICATION_JSON)
                .entity(new EpicErrorModel(serverError.ErrorCode, serverError.getMessage(),
                        serverError.MessageVars, serverError.NumericErrorCode, serverError.OriginatingService))
                .build();
    }
}
