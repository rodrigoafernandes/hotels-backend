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
public class HotelAvailDTO implements Serializable {

    private static final long serialVersionUID = 3125408658454154075L;

    private Long id;

    private String cityName;

    private List<RoomAvailDTO> rooms;

}