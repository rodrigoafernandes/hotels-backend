package br.com.gigiodesenvolvimento.hotelsbackend.service;

import br.com.gigiodesenvolvimento.hotelsbackend.broker.client.BrokerHotelWsClient;
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
import br.com.gigiodesenvolvimento.hotelsbackend.markup.MarkupCalculator;
import br.com.gigiodesenvolvimento.hotelsbackend.validator.EstimateRequestDataValidator;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.math.RoundingMode.HALF_UP;
import static java.util.Collections.synchronizedList;
import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.lang3.math.NumberUtils.createBigDecimal;

@ApplicationScoped
public class EstimateService {

    private static final int SCALE = 4;

    @Inject
    @RestClient
    BrokerHotelWsClient client;

    @Inject
    EstimateRequestDataConverter converter;

    @Inject
    MarkupCalculator markupCalculator;

    @Inject
    EstimateRequestDataValidator validator;

    public List<HotelAvailDTO> findByCity(SearchDataRequest searchDataRequest) {
        List<HotelAvailDTO> hotels = synchronizedList(new ArrayList<>());
        Set<ConstraintViolation<SearchData>> constraintViolations = new HashSet<>();

        SearchData searchData = converter.toSearchData(searchDataRequest, constraintViolations);

        verifyRequestErrors(constraintViolations);

        validator.validateCitySearch(searchData, constraintViolations);

        verifyRequestErrors(constraintViolations);

        List<HotelDTO> availabilityByCity = client.findByCityCode(searchData.getCityCode());

        if (isEmpty(availabilityByCity)) {
            throw new HotelNotFoundException();
        }

        availabilityByCity.stream().parallel().forEach(hotel -> {
            hotels.add(HotelAvailDTO.builder().cityName(hotel.getCityName()).id(hotel.getId())
                    .rooms(getAvailableRooms(hotel.getRooms(), searchData)).build());
        });

        return hotels.stream().sorted(comparingLong(HotelAvailDTO::getId)).collect(toList());
    }

    public HotelAvailDTO findByCityAndHotel(SearchDataRequest searchDataRequest) {
        Set<ConstraintViolation<SearchData>> constraintViolations = new HashSet<>();
        SearchData searchData = converter.toSearchDataHotel(searchDataRequest, constraintViolations);

        verifyRequestErrors(constraintViolations);

        validator.validateCityAndHotelSearch(searchData, constraintViolations);

        verifyRequestErrors(constraintViolations);

        HotelDTO hotelDetail = client.findByHotelCode(Long.valueOf(searchDataRequest.getHotelCodeRequest())).stream()
                .findFirst().orElseThrow(() -> new HotelNotFoundException());

        if (Long.valueOf(hotelDetail.getCityCode()).equals(searchData.getCityCode())) {
            HotelAvailDTO hotelAvail = HotelAvailDTO.builder().cityName(hotelDetail.getCityName())
                    .id(hotelDetail.getId()).build();

            hotelAvail.setRooms(getAvailableRooms(hotelDetail.getRooms(), searchData));

            return hotelAvail;
        } else {
            throw new HotelNotFoundException();
        }
    }

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

    protected void verifyRequestErrors(Set<ConstraintViolation<SearchData>> constraintViolations) {
        if (CollectionUtils.isNotEmpty(constraintViolations)) {
            throw new InvalidParametersException(constraintViolations);
        }
    }
}
