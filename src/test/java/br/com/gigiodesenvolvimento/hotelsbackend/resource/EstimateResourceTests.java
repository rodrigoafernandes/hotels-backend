package br.com.gigiodesenvolvimento.hotelsbackend.resource;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_MINUS_ONE;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_TWO;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;
import static org.apache.http.HttpStatus.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class EstimateResourceTests {

	@Test
	public void givenValidParameters_whenSearchAvailabilityByCity_thenShouldReturnHttpStatusOK() {
		String cityCode = "1";
		String startDate = "2019-12-06";
		String endDate = "2019-12-15";
		String qtGrowUp = INTEGER_TWO.toString();
		String qtChild = INTEGER_ONE.toString();
		given().pathParam("cityCode", cityCode).queryParam("qtGrowUp", qtGrowUp)
				.queryParam("startDate", startDate).queryParam("endDate", endDate).queryParam("qtChild", qtChild)
				.when().get("/estimate/city/{cityCode}").then().statusCode(SC_OK);
	}
	
	@Test
	public void givenInvalidRequestParameters_whenSearchAvailabilityByCity_thenShouldReturnHttpStatusPreconditionFailed() {
		String cityCode = "A";
		String startDate = "06/12/2019";
		String endDate = "2019/12/15";
		String qtGrowUp = INTEGER_ZERO.toString();
		String qtChild = INTEGER_MINUS_ONE.toString();
		given().pathParam("cityCode", cityCode).queryParam("qtGrowUp", qtGrowUp)
				.queryParam("startDate", startDate).queryParam("endDate", endDate).queryParam("qtChild", qtChild)
				.when().get("/estimate/city/{cityCode}").then().statusCode(SC_PRECONDITION_FAILED);
	}

	@Test
	public void givenInvalidDataParameters_whenSearchAvailabilityByCity_thenShouldReturnHttpStatusPreconditionFailed() {
		String cityCode = "1";
		String startDate = "2019-12-25";
		String endDate = "2019-12-15";
		String qtGrowUp = INTEGER_ZERO.toString();
		String qtChild = INTEGER_MINUS_ONE.toString();
		given().pathParam("cityCode", cityCode).queryParam("qtGrowUp", qtGrowUp)
				.queryParam("startDate", startDate).queryParam("endDate", endDate).queryParam("qtChild", qtChild)
				.when().get("/estimate/city/{cityCode}").then().statusCode(SC_PRECONDITION_FAILED);
	}

	@Test
	public void givenInvalidCityCode_whenSearchAvailabilityByCity_thenShouldReturnHttpStatusNotFound() {
		String cityCode = "-1";
		String startDate = "2019-12-06";
		String endDate = "2019-12-15";
		String qtGrowUp = INTEGER_ONE.toString();
		String qtChild = INTEGER_ZERO.toString();
		given().pathParam("cityCode", cityCode).queryParam("qtGrowUp", qtGrowUp)
				.queryParam("startDate", startDate).queryParam("endDate", endDate).queryParam("qtChild", qtChild)
				.when().get("/estimate/city/{cityCode}").then().statusCode(SC_NOT_FOUND);
	}

	@Test
	public void givenValidParameters_whenSearchAvailabilityByCityAndHotel_thenShouldReturnHttpStatusOK() {
		String cityCode = "1";
		String hotelCode = "1";
		String startDate = "2019-12-06";
		String endDate = "2019-12-15";
		String qtGrowUp = INTEGER_TWO.toString();
		String qtChild = INTEGER_ONE.toString();
		given().pathParam("cityCode", cityCode).pathParam("hotelCode", hotelCode)
				.queryParam("qtGrowUp", qtGrowUp).queryParam("startDate", startDate).queryParam("endDate", endDate)
				.queryParam("qtChild", qtChild).when().get("/estimate/city/{cityCode}/hotels/{hotelCode}")
			.then().statusCode(SC_OK);
	}

	@Test
	public void givenInvalidRequestParameters_whenSearchAvailabilityByCityAndHotel_thenShouldReturnHttpStatusPreconditionFailed() {
		String cityCode = "A";
		String hotelCode = "B";
		String startDate = "06/12/2019";
		String endDate = "2019/12/15";
		String qtGrowUp = "C";
		String qtChild = "D";
		given().pathParam("cityCode", cityCode).pathParam("hotelCode", hotelCode).queryParam("qtGrowUp", qtGrowUp)
				.queryParam("startDate", startDate).queryParam("endDate", endDate).queryParam("qtChild", qtChild)
				.when().get("/estimate/city/{cityCode}/hotels/{hotelCode}").then().statusCode(SC_PRECONDITION_FAILED);
	}

	@Test
	public void givenInvalidDataParameters_whenSearchAvailabilityByCityAndHotel_thenShouldReturnHttpStatusPreconditionFailed() {
		String cityCode = "1";
		String hotelCode = " ";
		String startDate = "2019-12-25";
		String endDate = "2019-12-15";
		String qtGrowUp = INTEGER_ZERO.toString();
		String qtChild = INTEGER_MINUS_ONE.toString();
		given().pathParam("cityCode", cityCode).pathParam("hotelCode", hotelCode).queryParam("qtGrowUp", qtGrowUp)
				.queryParam("startDate", startDate).queryParam("endDate", endDate).queryParam("qtChild", qtChild)
				.when().get("/estimate/city/{cityCode}/hotels/{hotelCode}").then().statusCode(SC_PRECONDITION_FAILED);
	}

	@Test
	public void givenInvalidCityCode_whenSearchAvailabilityByCityAndHotel_thenShouldReturnHttpStatusNotFound() {
		String cityCode = "1";
		String hotelCode = "3";
		String startDate = "2019-12-06";
		String endDate = "2019-12-15";
		String qtGrowUp = INTEGER_ONE.toString();
		String qtChild = INTEGER_ZERO.toString();
		given().pathParam("cityCode", cityCode).pathParam("hotelCode", hotelCode).queryParam("qtGrowUp", qtGrowUp)
				.queryParam("startDate", startDate).queryParam("endDate", endDate).queryParam("qtChild", qtChild)
				.when().get("/estimate/city/{cityCode}/hotels/{hotelCode}").then().statusCode(SC_NOT_FOUND);
	}

}
