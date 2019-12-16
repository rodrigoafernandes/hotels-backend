package br.com.gigiodesenvolvimento.hotelsbackend.service;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import br.com.gigiodesenvolvimento.hotelsbackend.broker.client.impl.BrokerHotelWSClientImpl;
import br.com.gigiodesenvolvimento.hotelsbackend.comission.ComissionCalculator;
import br.com.gigiodesenvolvimento.hotelsbackend.converter.EstimateRequestDataConverter;
import br.com.gigiodesenvolvimento.hotelsbackend.dto.HotelAvailDTO;
import br.com.gigiodesenvolvimento.hotelsbackend.dto.SearchDataRequest;
import br.com.gigiodesenvolvimento.hotelsbackend.exception.HotelNotFoundException;
import br.com.gigiodesenvolvimento.hotelsbackend.exception.InvalidParametersException;
import br.com.gigiodesenvolvimento.hotelsbackend.validator.EstimateRequestDataValidator;

public class EstimateServiceTests {

    private LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

    private EstimateService estimateService;

    @BeforeEach
    void setup() {
        validator.afterPropertiesSet();
        estimateService = new EstimateService(new BrokerHotelWSClientImpl(),
                new EstimateRequestDataValidator(validator), new EstimateRequestDataConverter(),
                new ComissionCalculator());
    }

    @Test
    public void givenInvalidParameters_whenEstimateHotelsByCity_thenShouldThrowInvalidParametersException() {
        SearchDataRequest searchData = SearchDataRequest.builder().build();
        assertThrows(InvalidParametersException.class, () -> {
            estimateService.estimateByCity(searchData);
        });

    }

    @Test
    public void givenNoResultsFromWS_whenEstimateHotelsByCity_thenShouldThrowHotelNotFoundException() {
        assertThrows(HotelNotFoundException.class, () -> {
            estimateService
                    .estimateByCity(SearchDataRequest.builder().cityCodeRequest("9626").endDateRequest("2019-11-10")
                            .startDateRequest("2019-11-01").qtdChildsRequest("0").qtdGrowUpsRequest("1").build());
        });
    }

    @Test
    public void givenValidParameters_whenEstimateHotelsByCity_thenShouldReturnListOfHotelAvailDTO() {
        List<HotelAvailDTO> estimateByCity = estimateService
                .estimateByCity(SearchDataRequest.builder().cityCodeRequest("1032").endDateRequest("2019-11-10")
                        .startDateRequest("2019-11-01").qtdChildsRequest("0").qtdGrowUpsRequest("1").build());
        assertTrue(isNotEmpty(estimateByCity));
    }

    @Test
    public void givenInvalidHotelCode_whenEstimateHotelByCityAndHotel_thenShouldThrowInvalidParametersException() {
        assertThrows(InvalidParametersException.class, () -> {
            estimateService.estimateByCityAndHotel(
                    SearchDataRequest.builder().cityCodeRequest("1032").endDateRequest("2019-11-10")
                            .startDateRequest("2019-11-01").qtdChildsRequest("0").qtdGrowUpsRequest("1").build());
        });
    }

    @Test
    public void givenInvalidCityCode_whenEstimateHotelByCityAndHotel_thenShouldThrowHotelNotFoundException() {
        assertThrows(HotelNotFoundException.class, () -> {
            estimateService.estimateByCityAndHotel(SearchDataRequest.builder().cityCodeRequest("9626")
                    .endDateRequest("2019-11-10").startDateRequest("2019-11-01").qtdChildsRequest("0")
                    .qtdGrowUpsRequest("1").hotelCodeRequest("1").build());
        });
    }

    @Test
    public void givenNoResultsFromWS_whenEstimateHotelByCityAndHotel_thenShouldThrowHotelNotFoundException() {
        assertThrows(HotelNotFoundException.class, () -> {
            estimateService.estimateByCityAndHotel(SearchDataRequest.builder().cityCodeRequest("1032")
                    .endDateRequest("2019-11-10").startDateRequest("2019-11-01").qtdChildsRequest("0")
                    .qtdGrowUpsRequest("1").hotelCodeRequest("2").build());
        });
    }

    @Test
    public void givenValidHotelCode_whenEstimateHotelByCityAndHotel_thenShouldReturnHotelAvailDTO() {

        HotelAvailDTO hotel = estimateService.estimateByCityAndHotel(SearchDataRequest.builder().cityCodeRequest("1032")
                .endDateRequest("2019-11-10").startDateRequest("2019-11-01").qtdChildsRequest("0")
                .qtdGrowUpsRequest("1").hotelCodeRequest("1").build());

        assertNotNull(hotel);
    }

}