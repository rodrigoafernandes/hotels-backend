package br.com.gigiodesenvolvimento.hotelsbackend.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(NOT_FOUND)
public class HotelNotFoundException extends HttpClientErrorException {

    private static final long serialVersionUID = 1681640448359695589L;

    public HotelNotFoundException() {
        super(NOT_FOUND, "Não foram localizados hotéis para a cidade informada");
    }

}
