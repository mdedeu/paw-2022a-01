package ar.edu.itba.paw.cryptuki.mapper;

import ar.edu.itba.paw.cryptuki.dto.GenericErrorDto;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotAllowedExceptionMapper implements ExceptionMapper<NotAllowedException> {

    private static final String MESSAGE = "HTTP method not allowed";

    @Override
    public Response toResponse(NotAllowedException e) {
        GenericErrorDto dto = GenericErrorDto.fromMessage(MESSAGE);
        return Response
                .status(Response.Status.METHOD_NOT_ALLOWED)
                .entity(dto)
                // Allow header required by RFC
                .allow(e.getResponse().getAllowedMethods())
                .build();
    }
}
