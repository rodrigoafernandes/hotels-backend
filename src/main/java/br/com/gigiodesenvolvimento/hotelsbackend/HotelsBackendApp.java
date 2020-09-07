package br.com.gigiodesenvolvimento.hotelsbackend;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

import javax.ws.rs.core.Application;

@OpenAPIDefinition(info = @Info(title = "Hotels-Backend", version = "2.0.0", description = "API para estimativa de " +
        "preço de hoteis de acordo com a cidade informada"))
public class HotelsBackendApp extends Application {

}
