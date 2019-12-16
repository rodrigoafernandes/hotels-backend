package br.com.gigiodesenvolvimento.hotelsbackend.resource;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gigiodesenvolvimento.hotelsbackend.dto.HotelAvailDTO;
import br.com.gigiodesenvolvimento.hotelsbackend.dto.SearchDataRequest;
import br.com.gigiodesenvolvimento.hotelsbackend.service.EstimateService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/estimate/city/{cityCode}")
public class EstimateResource {

	private final EstimateService estimateService;

	@GetMapping
	public ResponseEntity<List<HotelAvailDTO>> estimateCity(@PathVariable("cityCode") String cityCode,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestParam("qtdGrowUps") String qtdGrowUps, @RequestParam("qtdChilds") String qtdChilds) {
		return ResponseEntity.ok(estimateService
				.estimateByCity(SearchDataRequest.builder().cityCodeRequest(cityCode).startDateRequest(startDate)
						.endDateRequest(endDate).qtdGrowUpsRequest(qtdGrowUps).qtdChildsRequest(qtdChilds).build()));
	}

	@GetMapping("/hotels/{hotelCode}")
	public ResponseEntity<HotelAvailDTO> estimateHotel(@PathVariable("cityCode") String cityCode,
			@PathVariable("hotelCode") String hotelCode, @RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate, @RequestParam("qtdGrowUps") String qtdGrowUps,
			@RequestParam("qtdChilds") String qtdChilds) {
		return ResponseEntity.ok(estimateService.estimateByCityAndHotel(SearchDataRequest.builder()
				.cityCodeRequest(cityCode).hotelCodeRequest(hotelCode).startDateRequest(startDate)
				.endDateRequest(endDate).qtdGrowUpsRequest(qtdGrowUps).qtdChildsRequest(qtdChilds).build()));
	}

}
