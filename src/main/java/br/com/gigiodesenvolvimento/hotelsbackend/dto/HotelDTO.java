package br.com.gigiodesenvolvimento.hotelsbackend.dto;

import java.io.Serializable;
import java.util.List;

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
public class HotelDTO implements Serializable {

	private static final long serialVersionUID = -6038745602192066344L;
	
	private Long id;

	private String name;
	
	private Long cityCode;
	
	private String cityName;
	
	private List<RoomDTO> rooms;
	
}
