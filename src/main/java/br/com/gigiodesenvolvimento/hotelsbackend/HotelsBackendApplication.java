package br.com.gigiodesenvolvimento.hotelsbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import feign.Logger;

@EnableFeignClients
@SpringBootApplication
public class HotelsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelsBackendApplication.class, args);
	}

	@Bean
	Logger.Level feignLoggerLevel(){
		return Logger.Level.FULL;
	}
	
}
