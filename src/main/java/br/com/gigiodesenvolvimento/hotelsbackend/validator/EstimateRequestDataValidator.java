package br.com.gigiodesenvolvimento.hotelsbackend.validator;

import br.com.gigiodesenvolvimento.hotelsbackend.dto.SearchData;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.lang3.math.NumberUtils.LONG_ZERO;
import static org.hibernate.validator.internal.engine.ConstraintViolationImpl.forBeanValidation;
import static org.hibernate.validator.internal.engine.path.PathImpl.createPathFromString;

@ApplicationScoped
public class EstimateRequestDataValidator {

    @Inject
    Validator validator;

    public void validateCitySearch(SearchData searchData, Set<ConstraintViolation<SearchData>> constraintViolations) {
        constraintViolations.addAll(validator.validate(searchData));

        if (isEmpty(constraintViolations)) {
            if (LONG_ZERO >= searchData.getQtGrowUp()) {
                constraintViolations
                        .add(forBeanValidation("Parâmetros inválidos", null, null,
                                "A quantidade de adultos deve ser maior que zero", null, null,
                                null, null, createPathFromString("qtGrowUp"), null, null));
            }

            if (LONG_ZERO > searchData.getQtChild()) {
                constraintViolations
                        .add(forBeanValidation("Parâmetros inválidos", null, null,
                                "A quantidade de crianças deve ser maior ou igual a zero", null, null,
                                null, null, createPathFromString("qtChild"), null, null));
            }

            if (searchData.getStartDate().isAfter(searchData.getEndDate())) {
                constraintViolations
                        .add(forBeanValidation("Parâmetros inválidos", null, null,
                                "A data inicial não pode ser maior que a data final", null, null,
                                null, null, createPathFromString("startDate"), null, null));
            }

        }

    }

    public void validateCityAndHotelSearch(SearchData searchData,
                                           Set<ConstraintViolation<SearchData>> constraintViolations) {
        validateCitySearch(searchData, constraintViolations);

        if (searchData.getHotelCode() == null) {
            constraintViolations
                    .add(forBeanValidation("Parâmetros inválidos", null, null,
                            "O código do hotel não pode ser nulo", null, null,
                            null, null, createPathFromString("hotelCode"), null, null));
        }

    }

}
