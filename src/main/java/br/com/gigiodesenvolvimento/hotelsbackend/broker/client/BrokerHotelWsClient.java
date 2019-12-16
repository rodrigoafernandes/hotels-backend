package br.com.gigiodesenvolvimento.hotelsbackend.broker.client;

import br.com.gigiodesenvolvimento.hotelsbackend.dto.HotelDTO;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("/hotels")
@RegisterRestClient
public interface BrokerHotelWsClient {

    @GET
    @Path("/{hotelCode}")
    @Produces("application/json")
    List<HotelDTO> findByHotelCode(@PathParam("hotelCode") Long hotelCode);

    @GET
    @Path("/avail/{cityCode}")
    List<HotelDTO> findByCityCode(@PathParam("cityCode") Long cityCode);

}
