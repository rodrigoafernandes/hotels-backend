package br.com.gigiodesenvolvimento.hotelsbackend.exception;

import br.com.gigiodesenvolvimento.hotelsbackend.dto.ErrorMessageDTO;
import br.com.gigiodesenvolvimento.hotelsbackend.dto.SearchData;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;


public class InvalidParametersException extends RuntimeException {

	private static final long serialVersionUID = 8369374426344887730L;

	private Set<ConstraintViolation<SearchData>> constraintViolations;

    public InvalidParametersException(Set<ConstraintViolation<SearchData>> constraintViolations) {
        this.constraintViolations = constraintViolations;
    }

    public List<ErrorMessageDTO> getErrors() {
        return constraintViolations.stream().map(this::convert).collect(toList());
    }

    private ErrorMessageDTO convert(ConstraintViolation<SearchData> violation) {
        return ErrorMessageDTO.builder().property(violation.getPropertyPath().toString()).message(violation.getMessage()).build();
    }

}
