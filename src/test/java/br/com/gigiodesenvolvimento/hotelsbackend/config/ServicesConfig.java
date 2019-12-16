package br.com.gigiodesenvolvimento.hotelsbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import br.com.gigiodesenvolvimento.hotelsbackend.broker.client.impl.BrokerHotelWSClientImpl;
import br.com.gigiodesenvolvimento.hotelsbackend.comission.ComissionCalculator;
import br.com.gigiodesenvolvimento.hotelsbackend.converter.EstimateRequestDataConverter;
import br.com.gigiodesenvolvimento.hotelsbackend.service.EstimateService;
import br.com.gigiodesenvolvimento.hotelsbackend.validator.EstimateRequestDataValidator;

@Configuration
@Profile("test")
public class ServicesConfig {

    private LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

    @Bean
    public EstimateService estimateService() {
        validator.afterPropertiesSet();
        return new EstimateService(new BrokerHotelWSClientImpl(), new EstimateRequestDataValidator(validator),
                new EstimateRequestDataConverter(), new ComissionCalculator());
    }

}