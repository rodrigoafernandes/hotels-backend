package br.com.gigiodesenvolvimento.hotelsbackend.resource;

import br.com.gigiodesenvolvimento.hotelsbackend.dto.ErrorMessageDTO;
import br.com.gigiodesenvolvimento.hotelsbackend.dto.HotelAvailDTO;
import br.com.gigiodesenvolvimento.hotelsbackend.dto.SearchDataRequest;
import br.com.gigiodesenvolvimento.hotelsbackend.service.EstimateService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/estimate/city/{cityCode}")
public class EstimateResource {

	@Inject
	EstimateService estimateService;

	@GET
	@Tag(name = "Estimate")
	@Produces(APPLICATION_JSON)
	@Operation(description = "Realiza a estimativa de preço dos hoteis disponíveis para a cidade informada")
	@APIResponses({
			@APIResponse(responseCode = "200", description = "Lista de hoteis disponíveis com o preço total das " +
					"diárias e o preço da diária por adulto e por criança.",
					content = @Content(mediaType = APPLICATION_JSON,
							schema = @Schema(implementation = HotelAvailDTO[].class))),
			@APIResponse(responseCode = "404", description = "Hotéis não encontrados para a cidade informada"),
			@APIResponse(responseCode = "412", description = "Parâmetros inválidos informados na pesquisa",
					content = @Content(mediaType = APPLICATION_JSON,
							schema = @Schema(implementation = ErrorMessageDTO[].class)))
	})
	public List<HotelAvailDTO> estimateCity(@PathParam("cityCode") String cityCode,
											@QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate,
											@QueryParam("qtGrowUp") String qtGrowUp, @QueryParam("qtChild") String qtChild) {
		return estimateService.findByCity(SearchDataRequest.builder().cityCodeRequest(cityCode).startDateRequest(startDate).endDateRequest(endDate).qtGrowUpRequest(qtGrowUp).qtChildRequest(qtChild).build());
	}

	@GET
	@Tag(name = "Estimate")
	@Path("/hotels/{hotelCode}")
	@Produces(APPLICATION_JSON)
	@Operation(description = "Realiza a estimativa de preço do hotel informado")
	@APIResponses({
			@APIResponse(responseCode = "200", description = "Hoteis com o preço total das " +
					"diárias e o preço da diária por adulto e por criança.",
					content = @Content(mediaType = APPLICATION_JSON,
							schema = @Schema(implementation = HotelAvailDTO[].class))),
			@APIResponse(responseCode = "404", description = "Hotéis não encontrados para a cidade informada"),
			@APIResponse(responseCode = "412", description = "Parâmetros inválidos informados na pesquisa",
					content = @Content(mediaType = APPLICATION_JSON,
							schema = @Schema(implementation = ErrorMessageDTO[].class)))
	})
	public HotelAvailDTO estimateHotel(@PathParam("cityCode") String cityCode,
			@PathParam("hotelCode") String hotelCode, @QueryParam("startDate") String startDate,
			@QueryParam("endDate") String endDate, @QueryParam("qtGrowUp") String qtGrowUp,
			@QueryParam("qtChild") String qtChild) {
		return estimateService.findByCityAndHotel(SearchDataRequest.builder().cityCodeRequest(cityCode).hotelCodeRequest(hotelCode).startDateRequest(startDate).endDateRequest(endDate).qtGrowUpRequest(qtGrowUp).qtChildRequest(qtChild).build());
	}

}
