package br.com.gigiodesenvolvimento.hotelsbackend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ErrorMessageDTO implements Serializable {

	private static final long serialVersionUID = 6578442300650403767L;

	private String property;

    private String message;

}
