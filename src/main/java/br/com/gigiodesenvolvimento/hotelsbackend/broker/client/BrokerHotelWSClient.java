package br.com.gigiodesenvolvimento.hotelsbackend.broker.client;

import java.util.List;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.gigiodesenvolvimento.hotelsbackend.dto.HotelDTO;

@RefreshScope
@FeignClient(name = "broker-hotels-client", url = "${broker.hotels.url}")
public interface BrokerHotelWSClient {

	@GetMapping("/{hotelCode}")
	List<HotelDTO> findHotelDetail(@PathVariable("hotelCode") Long hotelCode);
	
	@GetMapping("/avail/{cityCode}")
	List<HotelDTO> findAvailableByCity(@PathVariable("cityCode") Long cityCode);
	
}
