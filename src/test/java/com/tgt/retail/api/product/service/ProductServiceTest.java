package com.tgt.retail.api.product.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import com.tgt.retail.api.product.repository.ProductRepository;
import com.tgt.retail.api.product.vo.Price;
import com.tgt.retail.api.product.vo.ProductPrice;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

	ProductServiceImpl productService = new ProductServiceImpl();
	
	@Mock
	private RestTemplate mockRestTemplate;

	@Mock
	ProductRepository mockProductrepository;

	@Mock
	private ProductClient productClient;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockRestTemplate = mock(RestTemplate.class);
	    productClient = new ProductClient();
	    productClient.setRestTemplate(mockRestTemplate);
	    productService.setProductRepository(mockProductrepository);
	}

	@Test
	public void getProductPriceByIdTest_validProductId() throws Exception {
		ProductPrice productPrice = new ProductPrice(13860416,
				new Price(BigDecimal.valueOf(2000), Currency.getInstance(Locale.getDefault()).getCurrencyCode()));
		Mockito.when(mockProductrepository.findProductPriceByProductId(Mockito.anyLong())).thenReturn(productPrice);
		ProductPrice actualPrice = productService.findProductPriceByProductId(Long.valueOf(13860416));
		assertEquals(productPrice.getProductId(), actualPrice.getProductId());
	}

	@Test
	public void getProductPriceByIdTest_inValidProductId() throws Exception {
		assertEquals(productService.findProductPriceByProductId(Long.valueOf(12345678)),null);
	}

}
