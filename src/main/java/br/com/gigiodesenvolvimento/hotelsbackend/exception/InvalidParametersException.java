package br.com.gigiodesenvolvimento.hotelsbackend.exception;

import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;

import java.util.List;

import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import lombok.Getter;

@Getter
@ResponseStatus(PRECONDITION_FAILED)
public class InvalidParametersException extends HttpClientErrorException {

    private static final long serialVersionUID = 1735914647987101163L;

    private List<ObjectError> listErrors;

    public InvalidParametersException(List<ObjectError> list) {
        super(PRECONDITION_FAILED);
        listErrors = list;
    }

}
