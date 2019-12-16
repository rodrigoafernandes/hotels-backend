package br.com.gigiodesenvolvimento.hotelsbackend;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

import javax.ws.rs.core.Application;

@OpenAPIDefinition(info = @Info(title = "Hotels-Backend", version = "0.0.1", description = "API para estimatima de " +
        "preço de hoteis de acordo com a cidade informada"))
public class HotelsBackendApp extends Application {

}
