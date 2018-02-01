package com.udemy.microservices;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CurrencyExchangeServiceProxy proxy;

	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(
			@PathVariable String from, 
			@PathVariable String to,
			@PathVariable BigDecimal quantity){
		System.out.println("in convert currency");
		
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		System.out.println("from " + from + " to " + to);

		System.out.println("in convert currency 1");
		
		ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class,
				uriVariables);
		
		System.out.println("in convert currency 2");
		
		CurrencyConversionBean response = responseEntity.getBody();
		return new CurrencyConversionBean(response.getId(), 
				from, 
				to, 
				quantity, 
				response.getConversionMultiple(), 
				quantity.multiply(response.getConversionMultiple()),
				response.getPort());
	}
	

	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrencyFeign(
			@PathVariable String from, 
			@PathVariable String to,
			@PathVariable BigDecimal quantity){
		
		CurrencyConversionBean response = proxy.retreiveExchangeValue(from, to);
		
		System.out.println("logging info");
		logger.info("conversion response {}", response);
		
		return new CurrencyConversionBean(response.getId(), 
				from, 
				to, 
				quantity, 
				response.getConversionMultiple(), 
				quantity.multiply(response.getConversionMultiple()),
				response.getPort());
	}
}
