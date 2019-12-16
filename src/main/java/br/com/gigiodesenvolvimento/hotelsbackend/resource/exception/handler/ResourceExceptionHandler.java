package br.com.gigiodesenvolvimento.hotelsbackend.resource.exception.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.gigiodesenvolvimento.hotelsbackend.exception.HotelNotFoundException;
import br.com.gigiodesenvolvimento.hotelsbackend.exception.InvalidParametersException;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class ResourceExceptionHandler {

    private final ObjectMapper mapper;

    @ExceptionHandler(HotelNotFoundException.class)
    public ResponseEntity<?> cityNotFound(HotelNotFoundException e) throws Exception {
        ResponseEntity<?> response = ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(mapper.readValue("{ \"defaultMessage\" : \"" + e.getMessage() + "\"}", JsonNode.class));

        return response;

    }

    @ExceptionHandler(InvalidParametersException.class)
    public ResponseEntity<?> invalidParameters(InvalidParametersException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getListErrors());
    }

}