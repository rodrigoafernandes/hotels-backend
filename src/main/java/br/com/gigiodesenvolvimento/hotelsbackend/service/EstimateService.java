package br.com.gigiodesenvolvimento.hotelsbackend.service;

import static java.lang.Long.valueOf;
import static java.math.RoundingMode.HALF_UP;
import static java.util.Collections.synchronizedList;
import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.lang3.math.NumberUtils.createBigDecimal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;

import br.com.gigiodesenvolvimento.hotelsbackend.broker.client.BrokerHotelWSClient;
import br.com.gigiodesenvolvimento.hotelsbackend.comission.ComissionCalculator;
import br.com.gigiodesenvolvimento.hotelsbackend.converter.EstimateRequestDataConverter;
import br.com.gigiodesenvolvimento.hotelsbackend.dto.HotelAvailDTO;
import br.com.gigiodesenvolvimento.hotelsbackend.dto.HotelDTO;
import br.com.gigiodesenvolvimento.hotelsbackend.dto.PriceDetailDTO;
import br.com.gigiodesenvolvimento.hotelsbackend.dto.RoomAvailDTO;
import br.com.gigiodesenvolvimento.hotelsbackend.dto.RoomDTO;
import br.com.gigiodesenvolvimento.hotelsbackend.dto.SearchData;
import br.com.gigiodesenvolvimento.hotelsbackend.dto.SearchDataRequest;
import br.com.gigiodesenvolvimento.hotelsbackend.exception.HotelNotFoundException;
import br.com.gigiodesenvolvimento.hotelsbackend.exception.InvalidParametersException;
import br.com.gigiodesenvolvimento.hotelsbackend.validator.EstimateRequestDataValidator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EstimateService {

	private static final int SCALE = 4;
	private final BrokerHotelWSClient client;
	private final EstimateRequestDataValidator validator;
	private final EstimateRequestDataConverter converter;
	private final ComissionCalculator comissionCalculator;

	public List<HotelAvailDTO> estimateByCity(SearchDataRequest searchDataRequest) {
		List<HotelAvailDTO> hotels = synchronizedList(new ArrayList<>());
		BindingResult bindingResult = new WebDataBinder(searchDataRequest).getBindingResult();

		SearchData searchData = converter.toSearchData(searchDataRequest, bindingResult);

		verifyRequestErrors(bindingResult);

		validator.validateCitySearch(searchData, bindingResult);

		verifyRequestErrors(bindingResult);

		List<HotelDTO> availabilityByCity = client.findAvailableByCity(valueOf(searchData.getCityCode()));

		if (isEmpty(availabilityByCity)) {
			throw new HotelNotFoundException();
		}

		availabilityByCity.parallelStream().forEach(hotel -> {
			hotels.add(HotelAvailDTO.builder().cityName(hotel.getCityName()).id(hotel.getId())
					.rooms(getAvailableRooms(hotel.getRooms(), searchData)).build());
		});

		return hotels.stream().sorted(comparingLong(HotelAvailDTO::getId)).collect(toList());
	}

	public HotelAvailDTO estimateByCityAndHotel(SearchDataRequest searchDataRequest) {
		BindingResult bindingResult = new WebDataBinder(searchDataRequest).getBindingResult();
		SearchData searchData = converter.toSearchDataHotel(searchDataRequest, bindingResult);

		verifyRequestErrors(bindingResult);

		validator.validateCityAndHotelSearch(searchData, bindingResult);

		verifyRequestErrors(bindingResult);

		HotelDTO hotelDetail = client.findHotelDetail(valueOf(searchData.getHotelCode())).stream().findFirst()
				.orElseThrow(() -> new HotelNotFoundException());

		if (valueOf(hotelDetail.getCityCode()).equals(searchData.getCityCode())) {

			HotelAvailDTO hotelAvail = HotelAvailDTO.builder().cityName(hotelDetail.getCityName())
					.id(hotelDetail.getId()).build();

			hotelAvail.setRooms(getAvailableRooms(hotelDetail.getRooms(), searchData));

			return hotelAvail;
		} else {
			throw new HotelNotFoundException();
		}
	}

	private List<RoomAvailDTO> getAvailableRooms(List<RoomDTO> rooms, SearchData searchData) {
		List<RoomAvailDTO> availableRooms = synchronizedList(new ArrayList<>());

		rooms.stream().parallel().forEach(room -> {
			BigDecimal roomAdultPriceWithTax = comissionCalculator.markup(room.getPrice().getAdult());

			BigDecimal roomChildPriceWithTax = comissionCalculator.markup(room.getPrice().getChild());

			BigDecimal totalPrice = roomAdultPriceWithTax
					.multiply(createBigDecimal(searchData.getDaysBetweenRequest().toString()))
					.multiply(createBigDecimal(searchData.getQtGrowUps().toString()))
					.add(roomChildPriceWithTax
							.multiply(createBigDecimal(searchData.getDaysBetweenRequest().toString()))
							.multiply(createBigDecimal(searchData.getQtChild().toString())))
					.setScale(SCALE, HALF_UP);

			availableRooms.add(RoomAvailDTO.builder().roomID(room.getRoomID()).categoryName(room.getCategoryName())
					.totalPrice(totalPrice).priceDetail(PriceDetailDTO.builder().pricePerDayAdult(roomAdultPriceWithTax)
							.pricePerDayChild(roomChildPriceWithTax).build())
					.build());
		});

		return availableRooms.stream().sorted(comparingLong(RoomAvailDTO::getRoomID)).collect(toList());
	}

	private void verifyRequestErrors(BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new InvalidParametersException(bindingResult.getAllErrors());
		}
	}

}
