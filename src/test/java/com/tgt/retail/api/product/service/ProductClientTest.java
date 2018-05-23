package com.tgt.retail.api.product.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.tgt.retail.api.product.exception.ProductNotFoundException;
import com.tgt.retail.api.product.service.ProductClient;
import com.tgt.retail.api.product.vo.Product;

@RunWith(MockitoJUnitRunner.class)
public class ProductClientTest {

	@Mock
	private RestTemplate mockRestTemplate;

	@InjectMocks
	private ProductClient productClient;

	@Before
	public void setup() {
		productClient.setRestTemplate(mockRestTemplate);
		MockitoAnnotations.initMocks(this);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void getProductDescriptionById_Success() {
		Product product = new Product(13860416, "Progressive power yoga:Sedona experie (DVD)");
		ResponseEntity<Product> response = new ResponseEntity<Product>(product, HttpStatus.ACCEPTED);
		Mockito.when(mockRestTemplate.exchange(Mockito.anyString(), Mockito.<HttpMethod>any(),
				Mockito.<HttpEntity<?>>any(), Mockito.<Class<?>>any(), Mockito.<String, String>anyMap()))
				.thenReturn((ResponseEntity) response);
		String productDescription = productClient.getProductNameById(Long.valueOf(13860416));
		assertEquals(productDescription, product.getName());
	}

	@Test(expected = Exception.class)
	public void getProductDescriptionById_Failure() {
		Product product = new Product(13860416, "Progressive power yoga:Sedona experie (DVD)");
		Mockito.when(mockRestTemplate.exchange(Mockito.anyString(), Mockito.<HttpMethod>any(),
				Mockito.<HttpEntity<?>>any(), Mockito.<Class<?>>any(), Mockito.<String, String>anyMap()))
				.thenThrow(new ProductNotFoundException(123456));
		String productDescription = productClient.getProductNameById(Long.valueOf(123456));
		assertNotEquals(productDescription, product.getName());
	}

}
