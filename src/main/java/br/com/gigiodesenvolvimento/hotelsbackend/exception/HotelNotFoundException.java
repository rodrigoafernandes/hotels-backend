package br.com.gigiodesenvolvimento.hotelsbackend.exception;

public class HotelNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4431339228103630900L;

	public HotelNotFoundException() {
        super("Não foram localizados hotéis para a cidade informada");
    }
}
