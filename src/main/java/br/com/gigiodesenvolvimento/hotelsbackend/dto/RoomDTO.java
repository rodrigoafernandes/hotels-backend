package br.com.gigiodesenvolvimento.hotelsbackend.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class RoomDTO implements Serializable {

	private static final long serialVersionUID = 175081951029481065L;

	private Long roomID;
	
	private String categoryName;
	
	private PriceDTO price;
	
}
