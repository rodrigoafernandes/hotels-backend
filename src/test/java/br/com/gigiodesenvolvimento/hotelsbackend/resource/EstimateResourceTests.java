package br.com.gigiodesenvolvimento.hotelsbackend.resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.gigiodesenvolvimento.hotelsbackend.HotelsBackendApplication;
import br.com.gigiodesenvolvimento.hotelsbackend.config.ServicesConfig;
import br.com.gigiodesenvolvimento.hotelsbackend.dto.SearchDataRequest;

@WebAppConfiguration
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
@TestPropertySource(properties = "broker.hotels.url=http://localhost:8546/hotels")
@ContextConfiguration(classes = { HotelsBackendApplication.class, ServicesConfig.class })
public class EstimateResourceTests {

        @Autowired
        private WebApplicationContext wac;

        private MockMvc mvc;

        @BeforeEach
        void setup() {
                mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        }

        @Test
        public void givenInvalidParameters_whenEstimateHotelsByCity_thenShouldReturnHttpStatusPreconditionFailed()
                        throws Exception {
                SearchDataRequest searchData = SearchDataRequest.builder().cityCodeRequest("A")
                                .startDateRequest("01-11-2019").endDateRequest("10-11-2019").qtdGrowUpsRequest("-1")
                                .qtdChildsRequest("-1").build();

                mvc.perform(get("/estimate/city/{cityCode}?startDate={startDate}&endDate={endDate}&qtdGrowUps={qtdGrowUps}&qtdChilds={qtdChilds}",
                                searchData.getCityCodeRequest(), searchData.getStartDateRequest(),
                                searchData.getEndDateRequest(), searchData.getQtdGrowUpsRequest(),
                                searchData.getQtdChildsRequest())).andExpect(status().isPreconditionFailed());

        }

        @Test
        public void givenValidParameters_whenEstimateHotelsByCity_thenShouldReturnHttpStatusOk() throws Exception {
                SearchDataRequest searchData = SearchDataRequest.builder().cityCodeRequest("1032")
                                .startDateRequest("2019-11-01").endDateRequest("2019-11-10").qtdGrowUpsRequest("1")
                                .qtdChildsRequest("0").build();

                mvc.perform(get("/estimate/city/{cityCode}?startDate={startDate}&endDate={endDate}&qtdGrowUps={qtdGrowUps}&qtdChilds={qtdChilds}",
                                searchData.getCityCodeRequest(), searchData.getStartDateRequest(),
                                searchData.getEndDateRequest(), searchData.getQtdGrowUpsRequest(),
                                searchData.getQtdChildsRequest())).andExpect(status().isOk());

        }

        @Test
        public void givenInvalidParameters_whenEstimateHotelsByCityAndHotel_thenShouldReturnHttpStatusNotFound()
                        throws Exception {
                SearchDataRequest searchData = SearchDataRequest.builder().cityCodeRequest("1032")
                                .startDateRequest("2019-11-01").endDateRequest("2019-11-10").qtdGrowUpsRequest("1")
                                .qtdChildsRequest("0").hotelCodeRequest("2").build();

                mvc.perform(get("/estimate/city/{cityCode}/hotels/{hotelCode}?startDate={startDate}&endDate={endDate}&qtdGrowUps={qtdGrowUps}&qtdChilds={qtdChilds}",
                                searchData.getCityCodeRequest(), searchData.getHotelCodeRequest(),
                                searchData.getStartDateRequest(), searchData.getEndDateRequest(),
                                searchData.getQtdGrowUpsRequest(), searchData.getQtdChildsRequest()))
                                .andExpect(status().isNotFound());

        }

        @Test
        public void givenValidParameters_whenEstimateHotelsByCityAndHotel_thenShouldReturnHttpStatusOk()
                        throws Exception {
                SearchDataRequest searchData = SearchDataRequest.builder().cityCodeRequest("1032")
                                .startDateRequest("2019-11-01").endDateRequest("2019-11-10").qtdGrowUpsRequest("1")
                                .qtdChildsRequest("0").hotelCodeRequest("1").build();

                mvc.perform(get("/estimate/city/{cityCode}/hotels/{hotelCode}?startDate={startDate}&endDate={endDate}&qtdGrowUps={qtdGrowUps}&qtdChilds={qtdChilds}",
                                searchData.getCityCodeRequest(), searchData.getHotelCodeRequest(),
                                searchData.getStartDateRequest(), searchData.getEndDateRequest(),
                                searchData.getQtdGrowUpsRequest(), searchData.getQtdChildsRequest()))
                                .andExpect(status().isOk());

        }

}