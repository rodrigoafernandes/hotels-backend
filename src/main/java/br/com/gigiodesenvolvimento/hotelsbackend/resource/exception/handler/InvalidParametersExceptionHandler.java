package br.com.gigiodesenvolvimento.hotelsbackend.resource.exception.handler;

import br.com.gigiodesenvolvimento.hotelsbackend.exception.InvalidParametersException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidParametersExceptionHandler implements ExceptionMapper<InvalidParametersException> {

    @Override
    public Response toResponse(InvalidParametersException e) {
        return Response.status(Response.Status.PRECONDITION_FAILED).type(MediaType.APPLICATION_JSON)
                .entity(e.getErrors()).build();
    }

}
