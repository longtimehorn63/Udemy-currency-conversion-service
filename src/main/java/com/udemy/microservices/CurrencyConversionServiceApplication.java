package com.udemy.microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients("com.udemy.microservices")
@EnableDiscoveryClient // enabling the discovery for the eureka server
public class CurrencyConversionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyConversionServiceApplication.class, args);
	}
	

	
	@Bean
	/*
	 * used for spring cloud sleuth or distributed tracing.
	 * The AlwaysSampler is for all messages
	 */
	public AlwaysSampler defaultSampler(){
		return new AlwaysSampler();
	}
}
