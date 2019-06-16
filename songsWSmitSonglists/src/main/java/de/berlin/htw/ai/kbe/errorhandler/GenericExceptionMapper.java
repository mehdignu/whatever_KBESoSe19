package de.berlin.htw.ai.kbe.errorhandler;

import de.berlin.htw.ai.kbe.model.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class GenericExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 400, "General Customized Message - Beautifying purposes only");
        return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage)
                .build();
    }

}
