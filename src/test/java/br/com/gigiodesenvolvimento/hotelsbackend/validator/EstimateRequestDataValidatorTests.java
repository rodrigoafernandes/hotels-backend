package br.com.gigiodesenvolvimento.hotelsbackend.validator;

import static org.apache.commons.lang3.math.NumberUtils.LONG_MINUS_ONE;
import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;
import static org.apache.commons.lang3.math.NumberUtils.LONG_ZERO;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.WebDataBinder;

import br.com.gigiodesenvolvimento.hotelsbackend.dto.SearchData;

public class EstimateRequestDataValidatorTests {

    private LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

    private EstimateRequestDataValidator estimateRequestDataValidator;

    @BeforeEach
    void setup() {
        validator.afterPropertiesSet();
        estimateRequestDataValidator = new EstimateRequestDataValidator(validator);
    }

    @Test
    public void givenStartDateAfterEndDate_whenValidateRequestSearchByCityAndHotel_thenBindingResultShouldHaveErrors() {
        SearchData searchData = SearchData.builder().cityCode(LONG_ONE).hotelCode(LONG_ONE).qtChild(LONG_ONE)
                .qtGrowUps(LONG_ONE).startDate(LocalDate.of(2019, 11, 15)).endDate(LocalDate.of(2019, 11, 13)).build();
        BindingResult bindingResult = new WebDataBinder(searchData).getBindingResult();
        estimateRequestDataValidator.validateCityAndHotelSearch(searchData, bindingResult);
        assertTrue(bindingResult.hasErrors());
    }

    @Test
    public void givenEndDateIsNull_whenValidateRequestSearchByCityAndHotel_thenBindingResultShouldHaveErrors() {
        SearchData searchData = SearchData.builder().cityCode(LONG_ONE).hotelCode(LONG_ONE).qtChild(LONG_ONE)
                .qtGrowUps(LONG_ONE).startDate(LocalDate.of(2019, 11, 13)).build();
        BindingResult bindingResult = new WebDataBinder(searchData).getBindingResult();
        estimateRequestDataValidator.validateCityAndHotelSearch(searchData, bindingResult);
        assertTrue(bindingResult.hasErrors());
    }

    @Test
    public void givenStartDateIsNull_whenValidateRequestSearchByCityAndHotel_thenBindingResultShouldHaveErrors() {
        SearchData searchData = SearchData.builder().cityCode(LONG_ONE).hotelCode(LONG_ONE).qtChild(LONG_ONE)
                .qtGrowUps(LONG_ONE).endDate(LocalDate.of(2019, 11, 13)).build();
        BindingResult bindingResult = new WebDataBinder(searchData).getBindingResult();
        estimateRequestDataValidator.validateCityAndHotelSearch(searchData, bindingResult);
        assertTrue(bindingResult.hasErrors());
    }

    @Test
    public void givenQtdGrowUpsIsNull_whenValidateRequestSearchByCityAndHotel_thenBindingResultShouldHaveErrors() {
        SearchData searchData = SearchData.builder().cityCode(LONG_ONE).hotelCode(LONG_ONE).qtChild(LONG_ONE)
                .startDate(LocalDate.of(2019, 11, 3)).endDate(LocalDate.of(2019, 11, 13)).build();
        BindingResult bindingResult = new WebDataBinder(searchData).getBindingResult();
        estimateRequestDataValidator.validateCityAndHotelSearch(searchData, bindingResult);
        assertTrue(bindingResult.hasErrors());
    }

    @Test
    public void givenQtdGrowUpsIsLessThanOne_whenValidateRequestSearchByCityAndHotel_thenBindingResultShouldHaveErrors() {
        SearchData searchData = SearchData.builder().cityCode(LONG_ONE).hotelCode(LONG_ONE).qtChild(LONG_ONE)
                .qtGrowUps(LONG_ZERO).startDate(LocalDate.of(2019, 11, 3)).endDate(LocalDate.of(2019, 11, 13)).build();
        BindingResult bindingResult = new WebDataBinder(searchData).getBindingResult();
        estimateRequestDataValidator.validateCityAndHotelSearch(searchData, bindingResult);
        assertTrue(bindingResult.hasErrors());
    }

    @Test
    public void givenQtdChildsUpsIsNull_whenValidateRequestSearchByCityAndHotel_thenBindingResultShouldHaveErrors() {
        SearchData searchData = SearchData.builder().cityCode(LONG_ONE).hotelCode(LONG_ONE).qtGrowUps(LONG_ONE)
                .startDate(LocalDate.of(2019, 11, 3)).endDate(LocalDate.of(2019, 11, 13)).build();
        BindingResult bindingResult = new WebDataBinder(searchData).getBindingResult();
        estimateRequestDataValidator.validateCityAndHotelSearch(searchData, bindingResult);
        assertTrue(bindingResult.hasErrors());
    }

    @Test
    public void givenQtdChildsUpsIsLessThanZero_whenValidateRequestSearchByCityAndHotel_thenBindingResultShouldHaveErrors() {
        SearchData searchData = SearchData.builder().cityCode(LONG_ONE).hotelCode(LONG_ONE).qtGrowUps(LONG_ONE)
                .qtChild(LONG_MINUS_ONE).startDate(LocalDate.of(2019, 11, 3)).endDate(LocalDate.of(2019, 11, 13))
                .build();
        BindingResult bindingResult = new WebDataBinder(searchData).getBindingResult();
        estimateRequestDataValidator.validateCityAndHotelSearch(searchData, bindingResult);
        assertTrue(bindingResult.hasErrors());
    }

}