package br.com.gigiodesenvolvimento.hotelsbackend.converter;

import static java.lang.Long.valueOf;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import br.com.gigiodesenvolvimento.hotelsbackend.dto.SearchData;
import br.com.gigiodesenvolvimento.hotelsbackend.dto.SearchDataRequest;

@Component
public class EstimateRequestDataConverter {

    public SearchData toSearchData(SearchDataRequest searchDataRequest, BindingResult bindingResult) {
        return SearchData.builder()
                .cityCode(convertRequestToLong(searchDataRequest.getCityCodeRequest(), "cityCode", bindingResult))
                .startDate(
                        convertRequestToLocalDate(searchDataRequest.getStartDateRequest(), "startDate", bindingResult))
                .endDate(convertRequestToLocalDate(searchDataRequest.getEndDateRequest(), "endDate", bindingResult))
                .qtGrowUps(convertRequestToLong(searchDataRequest.getQtdGrowUpsRequest(), "qtdGrowUps", bindingResult))
                .qtChild(convertRequestToLong(searchDataRequest.getQtdChildsRequest(), "qtdChilds", bindingResult))
                .build();
    }

    public SearchData toSearchDataHotel(SearchDataRequest searchDataRequest, BindingResult bindingResult) {
        SearchData searchData = toSearchData(searchDataRequest, bindingResult);

        if (isNotBlank(searchDataRequest.getHotelCodeRequest())) {
            searchData.setHotelCode(
                    convertRequestToLong(searchDataRequest.getHotelCodeRequest(), "hotelCode", bindingResult));
        }

        return searchData;
    }

    private Long convertRequestToLong(String numberRequest, String propertyName, BindingResult bindingResult) {
        if (isNotBlank(numberRequest)) {
            try {
                return valueOf(numberRequest);
            } catch (NumberFormatException e) {
                bindingResult.addError(new ObjectError("searchData",
                        String.format("O campo $s deve possuir valor númerico e inteiro", propertyName)));
            }
        }
        return null;
    }

    private LocalDate convertRequestToLocalDate(String dateRequest, String propertyName, BindingResult bindingResult) {
        if (isNotBlank(dateRequest)) {
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
            try {
                return LocalDate.from(dtf.parse(dateRequest));
            } catch (DateTimeParseException e) {
                bindingResult.addError(new ObjectError("searchData",
                        String.format("O campo %s deve possuir o formato %s", propertyName, pattern)));
            }
        }
        return null;
    }

}
