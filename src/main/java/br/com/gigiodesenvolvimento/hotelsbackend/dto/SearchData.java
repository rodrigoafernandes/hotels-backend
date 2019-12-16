package br.com.gigiodesenvolvimento.hotelsbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SearchData implements Serializable {

	private static final long serialVersionUID = -1928014549223580111L;

	@NotNull
	private Long cityCode;

	@NotNull
	private LocalDate startDate;

	@NotNull
	private LocalDate endDate;

	@NotNull
	private Long qtGrowUps;

	@NotNull
	private Long qtChild;

	private Long hotelCode;

	public Long getDaysBetweenRequest() {
		return DAYS.between(startDate, endDate);
	}

}
