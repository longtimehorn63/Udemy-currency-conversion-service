package com.udemy.microservices;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name="currency-exchange-service", url="localhost:8000")
// feign when calling the service directly
//@FeignClient(name="currency-exchange-service")
// when using the API gateway (Zuul
@FeignClient(name="netflix-zuul-api-gateway-server")
// for ribbon
@RibbonClient(name="currency-exchange-service")
public interface CurrencyExchangeServiceProxy {

	// when calling directly
//	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	//when using zuul
	@GetMapping("/currency-exchange-service/currency-exchange/from/{from}/to/{to}")
	
	public CurrencyConversionBean retreiveExchangeValue(@PathVariable("from") String from, @PathVariable("to") String to);
}
