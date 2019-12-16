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
public class SearchDataRequest implements Serializable {

	private static final long serialVersionUID = -7558984154084392478L;

	private String cityCodeRequest;

	private String startDateRequest;

	private String endDateRequest;

	private String qtGrowUpRequest;

	private String qtChildRequest;

	private String hotelCodeRequest;

}
