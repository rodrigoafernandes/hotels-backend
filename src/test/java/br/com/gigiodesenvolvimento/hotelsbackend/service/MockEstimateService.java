package br.com.gigiodesenvolvimento.hotelsbackend.service;

import br.com.gigiodesenvolvimento.hotelsbackend.converter.EstimateRequestDataConverter;
import br.com.gigiodesenvolvimento.hotelsbackend.dto.*;
import br.com.gigiodesenvolvimento.hotelsbackend.exception.HotelNotFoundException;
import br.com.gigiodesenvolvimento.hotelsbackend.markup.MarkupCalculator;
import br.com.gigiodesenvolvimento.hotelsbackend.validator.EstimateRequestDataValidator;
import io.quarkus.test.Mock;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static java.math.RoundingMode.HALF_UP;
import static java.util.Arrays.asList;
import static java.util.Collections.synchronizedList;
import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;
import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;
import static org.apache.commons.lang3.math.NumberUtils.createBigDecimal;

@Mock
@ApplicationScoped
public class MockEstimateService extends EstimateService {

	private static final int SCALE = 4;

	List<HotelAvailDTO> hotels = new ArrayList<>();
	List<RoomDTO> rooms = new ArrayList<>();

	public MockEstimateService() {
	    rooms.addAll(asList(RoomDTO.builder().categoryName("LUXO").roomID(LONG_ONE).price(PriceDTO.builder()
                .adult(createBigDecimal("100")).child(createBigDecimal("50")).build()).build()));
		hotels.addAll(asList(HotelAvailDTO.builder().id(LONG_ONE)
				.cityName("1 - PORTO SEGURO").build()));
	}

	@Inject
    EstimateRequestDataConverter converter;

    @Inject
    MarkupCalculator markupCalculator;

    @Inject
	EstimateRequestDataValidator validator;
	
	@Override
	public List<HotelAvailDTO> findByCity(SearchDataRequest searchDataRequest) {
		Set<ConstraintViolation<SearchData>> constraintViolations = new HashSet<>();
		SearchData searchData = converter.toSearchData(searchDataRequest, constraintViolations);
		verifyRequestErrors(constraintViolations);
		validator.validateCitySearch(searchData, constraintViolations);
		verifyRequestErrors(constraintViolations);
		List<HotelAvailDTO> availDTOS = hotels.stream()
				.filter(hotel -> containsIgnoreCase(hotel.getCityName(), searchDataRequest.getCityCodeRequest()))
				.collect(toList());

		if (CollectionUtils.isEmpty(availDTOS)) {
			throw new HotelNotFoundException();
		}

		return availDTOS;
	}
	
	@Override
	public HotelAvailDTO findByCityAndHotel(SearchDataRequest searchDataRequest) {
		Set<ConstraintViolation<SearchData>> constraintViolations = new HashSet<>();
		SearchData searchData = converter.toSearchDataHotel(searchDataRequest, constraintViolations );
		verifyRequestErrors(constraintViolations);
		validator.validateCityAndHotelSearch(searchData, constraintViolations);
		verifyRequestErrors(constraintViolations);
		List<HotelAvailDTO> hotelsAvail = hotels.stream()
				.filter(hotel -> containsIgnoreCase(hotel.getCityName(), searchDataRequest.getCityCodeRequest()))
				.collect(toList());
		return hotelsAvail.stream().filter(hotel -> hotel.getId().equals(searchData.getHotelCode()))
				.findFirst().orElseThrow(() -> new HotelNotFoundException())
				.setRooms(getAvailableRooms(rooms, searchData));
	}

	@Override
	protected List<RoomAvailDTO> getAvailableRooms(List<RoomDTO> rooms, SearchData searchData) {
		List<RoomAvailDTO> availableRooms = synchronizedList(new ArrayList<>());

		rooms.stream().parallel().forEach(room -> {
			BigDecimal roomAdultPriceWithTax = markupCalculator.calculate(room.getPrice().getAdult());

			BigDecimal roomChildPriceWithTax = markupCalculator.calculate(room.getPrice().getChild());

			BigDecimal totalPrice = roomAdultPriceWithTax
					.multiply(createBigDecimal(searchData.getDaysBetweenRequest().toString()))
					.multiply(createBigDecimal(searchData.getQtGrowUp().toString()))
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
}
