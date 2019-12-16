package br.com.gigiodesenvolvimento.hotelsbackend.resource.exception.handler;

import br.com.gigiodesenvolvimento.hotelsbackend.exception.HotelNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Provider
public class HotelNotFoundExceptionHandler implements ExceptionMapper<HotelNotFoundException> {

    @Override
    public Response toResponse(HotelNotFoundException e) {
        return Response.status(NOT_FOUND).type(APPLICATION_JSON)
                .entity("{ \"defaultMessage\" : \"" + e.getMessage() + "\"}").build();
    }

}
