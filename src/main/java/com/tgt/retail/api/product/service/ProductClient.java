package com.tgt.retail.api.product.service;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.tgt.retail.api.product.exception.ProductNotFoundException;
import com.tgt.retail.api.product.vo.Product;

/*
 * This is wrapper class to submit the request to and receive response from redsky product endpoint using Rest Template to submit the REST API request
 */
@Component
public class ProductClient {

	Logger logger = Logger.getLogger(ProductClient.class.getName());

	public static final String REDSKY_PRODUCT_ENDPOINT_URI = "https://redsky.target.com/v1/pdp/tcin/{id}?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";

	@Autowired
	private RestTemplate restTemplate;

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	/**
	 * To connect with redsky product endpoint for the product informaiton
	 * 
	 * @param id
	 * @return
	 */
	public String getProductNameById(Long id) {
		String productDescription = null;
		try {
			Map<String, Long> uriVariables = new HashMap<>();
			uriVariables.put("id", (long) id);
			ResponseEntity<Product> result = restTemplate.exchange(REDSKY_PRODUCT_ENDPOINT_URI, HttpMethod.GET,
					getRequestEntity(), Product.class, uriVariables);
			Product product = result.getBody();
			productDescription = product.getName();
		} catch (HttpClientErrorException httpClientErrorException) {
			if (httpClientErrorException.getMessage().equalsIgnoreCase("404 Not Found")) {
				throw new ProductNotFoundException(id);
			}
		}
		return productDescription;
	}

	public HttpEntity<Object> getRequestEntity() {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(headers);
		return requestEntity;
	}
}
