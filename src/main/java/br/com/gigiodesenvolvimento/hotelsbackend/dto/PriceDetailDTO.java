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
public class PriceDetailDTO implements Serializable {

    private static final long serialVersionUID = 2902840154113626874L;

    private BigDecimal pricePerDayAdult;

    private BigDecimal pricePerDayChild;

}
