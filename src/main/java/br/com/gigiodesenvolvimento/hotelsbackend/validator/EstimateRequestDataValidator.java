package br.com.gigiodesenvolvimento.hotelsbackend.validator;

import static org.apache.commons.lang3.math.NumberUtils.LONG_ZERO;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import br.com.gigiodesenvolvimento.hotelsbackend.dto.SearchData;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EstimateRequestDataValidator {

	private final Validator validator;

	public void validateCitySearch(SearchData searchData, BindingResult bindingResult) {

		validator.validate(searchData, bindingResult);

		if (!bindingResult.hasErrors()) {

			if (LONG_ZERO >= searchData.getQtGrowUps()) {
				bindingResult
						.addError(new ObjectError("qtdGrowUps", "A quantidade de adultos deve ser maior que zero"));
			}

			if (LONG_ZERO > searchData.getQtChild()) {
				bindingResult.addError(
						new ObjectError("qtdChilds", "A quantidade de crianças deve ser maior ou igual a zero"));
			}

			if (searchData.getStartDate().isAfter(searchData.getEndDate())) {
				bindingResult
						.addError(new ObjectError("startDate", "A data inicial não pode ser maior que a data final"));
			}

		}
	}

	public void validateCityAndHotelSearch(SearchData searchData, BindingResult bindingResult) {
		validateCitySearch(searchData, bindingResult);

		if (searchData.getHotelCode() == null) {
			bindingResult.addError(new ObjectError("hotelCode", "O código do hotel não pode ser nulo"));
		}

	}

}
