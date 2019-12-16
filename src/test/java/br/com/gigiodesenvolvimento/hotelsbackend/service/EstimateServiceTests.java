package br.com.gigiodesenvolvimento.hotelsbackend.service;

import br.com.gigiodesenvolvimento.hotelsbackend.broker.client.BrokerHotelWsClient;
import br.com.gigiodesenvolvimento.hotelsbackend.converter.EstimateRequestDataConverter;
import br.com.gigiodesenvolvimento.hotelsbackend.dto.*;
import br.com.gigiodesenvolvimento.hotelsbackend.exception.HotelNotFoundException;
import br.com.gigiodesenvolvimento.hotelsbackend.markup.MarkupCalculator;
import br.com.gigiodesenvolvimento.hotelsbackend.validator.EstimateRequestDataValidator;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static java.math.RoundingMode.HALF_UP;
import static java.util.Arrays.asList;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.math.NumberUtils.*;
import static org.apache.commons.lang3.math.NumberUtils.createInteger;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings({"unchecked", "rawtypes"})
public class EstimateServiceTests {

    private static final Integer PRECISION = createInteger("8");
    private static final Integer SCALE = createInteger("4");

    @Mock
    BrokerHotelWsClient client;

    @Mock
    EstimateRequestDataConverter converter;

    @Mock
    MarkupCalculator markupCalculator;

    @Mock
    EstimateRequestDataValidator validator;

    @InjectMocks
    private EstimateService estimateService;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    public void givenValidSearchParameters_whenFindByCity_thenShouldReturnsListOfHotelAvailDTO() {
        Long cityCode = LONG_ONE;
        SearchDataRequest searchDataRequest = SearchDataRequest.builder().build();
        SearchData searchData = SearchData.builder().cityCode(cityCode).qtChild(LONG_ONE).qtGrowUp(LONG_ONE)
                .startDate(LocalDate.now()).endDate(LocalDate.now().plusDays(LONG_ONE)).build();
        when(converter.toSearchData(any(SearchDataRequest.class), any(Set.class)))
                .thenReturn(searchData);
        doNothing().when(validator).validateCitySearch(any(SearchData.class), any(Set.class));
        when(client.findByCityCode(cityCode)).thenReturn(asList(HotelDTO.builder().cityCode(LONG_ONE).cityName("TESTE")
                .rooms(asList(RoomDTO.builder().roomID(LONG_ONE).categoryName("LUXO").price(PriceDTO.builder()
                        .child(createBigDecimal("100")).adult(createBigDecimal("100")).build()).build())).build()));
        when(markupCalculator.calculate(any(BigDecimal.class))).thenReturn(createBigDecimal("100")
                .divide(createBigDecimal("0.7"), new MathContext(PRECISION)).setScale(SCALE, HALF_UP));
        List<HotelAvailDTO> hotels = estimateService.findByCity(searchDataRequest);
        assertTrue(isNotEmpty(hotels));
    }

    @Test
    public void givenNoResults_whenFindByCity_thenShouldThrowsHotelNotFoundException() {
        Long cityCode = LONG_ONE;
        SearchDataRequest searchDataRequest = SearchDataRequest.builder().build();
        SearchData searchData = SearchData.builder().cityCode(cityCode).qtChild(LONG_ONE).qtGrowUp(LONG_ONE)
                .startDate(LocalDate.now()).endDate(LocalDate.now().plusDays(LONG_ONE)).build();
        when(converter.toSearchData(any(SearchDataRequest.class), any(Set.class)))
                .thenReturn(searchData);
        doNothing().when(validator).validateCitySearch(any(SearchData.class), any(Set.class));
        when(client.findByCityCode(cityCode)).thenReturn(new ArrayList<>());

        Assertions.assertThrows(HotelNotFoundException.class, () ->{
            estimateService.findByCity(searchDataRequest);
        });

    }

    @Test
    public void givenValidSearchParameters_whenFindByCityAndHotel_thenShouldReturnsHotelAvailDTO() {
        Long cityCode = LONG_ONE;
        Long hotelCode = LONG_ONE;
        SearchDataRequest searchDataRequest = SearchDataRequest.builder().hotelCodeRequest(hotelCode.toString())
                .cityCodeRequest(cityCode.toString()).build();
        SearchData searchData = SearchData.builder().cityCode(cityCode).hotelCode(hotelCode).qtChild(LONG_ONE)
                .qtGrowUp(LONG_ONE).startDate(LocalDate.now()).endDate(LocalDate.now().plusDays(LONG_ONE)).build();
        when(converter.toSearchDataHotel(any(SearchDataRequest.class), any(Set.class)))
                .thenReturn(searchData);
        doNothing().when(validator).validateCityAndHotelSearch(any(SearchData.class), any(Set.class));
        when(client.findByHotelCode(hotelCode)).thenReturn(asList(HotelDTO.builder().cityCode(LONG_ONE).cityName("TESTE")
                .rooms(asList(RoomDTO.builder().roomID(LONG_ONE).categoryName("LUXO").price(PriceDTO.builder()
                        .child(createBigDecimal("100")).adult(createBigDecimal("100")).build()).build())).build()));
        when(markupCalculator.calculate(any(BigDecimal.class))).thenReturn(createBigDecimal("100")
                .divide(createBigDecimal("0.7"), new MathContext(PRECISION)).setScale(SCALE, HALF_UP));
        HotelAvailDTO hotel = estimateService.findByCityAndHotel(searchDataRequest);
        assertNotNull(hotel);
    }

    @Test
    public void givenInvalidHotelCode_whenFindByCityAndHotel_thenShouldThrowsHotelNotFoundException() {
        Long cityCode = LONG_ONE;
        Long hotelCode = LONG_ONE;
        SearchDataRequest searchDataRequest = SearchDataRequest.builder().hotelCodeRequest(hotelCode.toString())
                .cityCodeRequest(cityCode.toString()).build();
        SearchData searchData = SearchData.builder().cityCode(cityCode).hotelCode(hotelCode).qtChild(LONG_ONE)
                .qtGrowUp(LONG_ONE).startDate(LocalDate.now()).endDate(LocalDate.now().plusDays(LONG_ONE)).build();
        when(converter.toSearchDataHotel(any(SearchDataRequest.class), any(Set.class)))
                .thenReturn(searchData);
        doNothing().when(validator).validateCityAndHotelSearch(any(SearchData.class), any(Set.class));
        when(client.findByHotelCode(hotelCode)).thenReturn(new ArrayList<>());
        assertThrows(HotelNotFoundException.class, () -> {
            estimateService.findByCityAndHotel(searchDataRequest);
        });

    }

    @Test
    public void givenInvalidCityCode_whenFindByCityAndHotel_thenShouldThrowsHotelNotFoundException() {
        Long cityCode = LONG_ONE;
        Long hotelCode = LONG_ONE;
        SearchDataRequest searchDataRequest = SearchDataRequest.builder().hotelCodeRequest(hotelCode.toString())
                .cityCodeRequest(cityCode.toString()).build();
        SearchData searchData = SearchData.builder().cityCode(cityCode).hotelCode(hotelCode).qtChild(LONG_ONE)
                .qtGrowUp(LONG_ONE).startDate(LocalDate.now()).endDate(LocalDate.now().plusDays(LONG_ONE)).build();
        when(converter.toSearchDataHotel(any(SearchDataRequest.class), any(Set.class)))
                .thenReturn(searchData);
        doNothing().when(validator).validateCityAndHotelSearch(any(SearchData.class), any(Set.class));
        when(client.findByHotelCode(hotelCode)).thenReturn(asList(HotelDTO.builder().cityCode(createLong("2"))
                .cityName("TESTE").rooms(asList(RoomDTO.builder().roomID(LONG_ONE).categoryName("LUXO")
                        .price(PriceDTO.builder().child(createBigDecimal("100")).adult(createBigDecimal("100"))
                                .build()).build())).build()));
        assertThrows(HotelNotFoundException.class, () -> {
            estimateService.findByCityAndHotel(searchDataRequest);
        });
    }

}
