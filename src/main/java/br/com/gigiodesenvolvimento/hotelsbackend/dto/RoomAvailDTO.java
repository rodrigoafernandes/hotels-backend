package br.com.gigiodesenvolvimento.hotelsbackend.dto;

import java.io.Serializable;
import java.math.BigDecimal;

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
public class RoomAvailDTO implements Serializable {

    private static final long serialVersionUID = 7832547379581780781L;

    private Long roomID;

    private String categoryName;

    private BigDecimal totalPrice;

    private PriceDetailDTO priceDetail; 

}
