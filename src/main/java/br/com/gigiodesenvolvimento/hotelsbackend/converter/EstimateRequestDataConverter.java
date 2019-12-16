package br.com.gigiodesenvolvimento.hotelsbackend.converter;

import br.com.gigiodesenvolvimento.hotelsbackend.dto.SearchData;
import br.com.gigiodesenvolvimento.hotelsbackend.dto.SearchDataRequest;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.ConstraintViolation;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Set;

import static java.lang.Long.valueOf;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@ApplicationScoped
public class EstimateRequestDataConverter {

    public SearchData toSearchData(SearchDataRequest searchDataRequest, Set<ConstraintViolation<SearchData>> constraintViolations) {
        SearchData searchData = SearchData.builder().cityCode(convertRequestToLong(searchDataRequest.getCityCodeRequest(), "cityCode", constraintViolations))
                .startDate(
                        convertRequestToLocalDate(searchDataRequest.getStartDateRequest(), "startDate", constraintViolations))
                .endDate(convertRequestToLocalDate(searchDataRequest.getEndDateRequest(), "endDate", constraintViolations))
                .qtGrowUp(convertRequestToLong(searchDataRequest.getQtGrowUpRequest(), "qtdGrowUps", constraintViolations))
                .qtChild(convertRequestToLong(searchDataRequest.getQtChildRequest(), "qtdChilds", constraintViolations)).build();

        return searchData;
    }

    public SearchData toSearchDataHotel(SearchDataRequest searchDataRequest, Set<ConstraintViolation<SearchData>> constraintViolations) {
        SearchData searchData = toSearchData(searchDataRequest, constraintViolations);

        if (isNotBlank(searchDataRequest.getHotelCodeRequest())) {
            searchData.setHotelCode(
                    convertRequestToLong(searchDataRequest.getHotelCodeRequest(), "hotelCode", constraintViolations));
        }

        return searchData;
    }

    private Long convertRequestToLong(String numberRequest, String propertyName, Set<ConstraintViolation<SearchData>> constraintViolations) {
        if (isNotBlank(numberRequest)) {
            try {
                return valueOf(numberRequest);
            } catch (NumberFormatException e) {
                constraintViolations.add(ConstraintViolationImpl.forBeanValidation("Parâmetros inválidos", null, null,
                        String.format("O campo %s deve possuir valor númerico e inteiro", propertyName), null, null,
                        null, null, PathImpl.createPathFromString(propertyName), null, null));
            }
        }
        return null;
    }

    private LocalDate convertRequestToLocalDate(String dateRequest, String propertyName, Set<ConstraintViolation<SearchData>> constraintViolations) {
        if (isNotBlank(dateRequest)) {
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
            try {
                return LocalDate.from(dtf.parse(dateRequest));
            } catch (DateTimeParseException e) {
                constraintViolations.add(ConstraintViolationImpl.forBeanValidation("Parâmetros inválidos", null, null,
                        String.format("O campo %s deve possuir o formato %s", propertyName, pattern), null, null,
                        null, null, PathImpl.createPathFromString(propertyName), null, null));
            }
        }
        return null;
    }

}
