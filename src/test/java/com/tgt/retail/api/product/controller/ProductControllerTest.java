package com.tgt.retail.api.product.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgt.retail.api.product.constants.MessageConstants;
import com.tgt.retail.api.product.service.ProductService;
import com.tgt.retail.api.product.validator.ProductValidator;
import com.tgt.retail.api.product.vo.Price;
import com.tgt.retail.api.product.vo.ProductPrice;
import com.tgt.retail.api.product.vo.ResponseInfo;

/**
 * Product Controller slice Testing
 */
public class ProductControllerTest {

	Logger logger = Logger.getLogger(ProductControllerTest.class.getName());

	@Autowired
	private ProductController productController;

	private ProductService productService;

	private MockMvc mockMvc;

	String priceJson;

	ObjectMapper objMapper = new ObjectMapper();

	/**
	 * Mock Setup the dependencies
	 */
	@Before
	public void setup() {
		ProductPrice productPrice = new ProductPrice(13860416, "Progressive power yoga:Sedona experie (DVD)",
				new Price(BigDecimal.valueOf(1000), Currency.getInstance(Locale.getDefault()).getCurrencyCode()));
		productService = mock(ProductService.class);
		productController = new ProductController();
		productController.setProductService(productService);
		productController.setProductValidator(new ProductValidator());
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
		try {
			priceJson = objMapper.writeValueAsString(productPrice);
		} catch (JsonProcessingException e) {
			logger.warning("JsonProcessingException occurred ");
		}
	}

	/**
	 * Validating the getProduct Request for the valid product ID
	 * 
	 * @throws Exception
	 */
	@Test
	public void getProductsTest_ForValidProduct() throws Exception {
		when(productService.getProductInfo("13860416")).thenReturn(new ProductPrice(13860416,
				"Progressive power yoga:Sedona experie (DVD)",
				new Price(BigDecimal.valueOf(1000), Currency.getInstance(Locale.getDefault()).getCurrencyCode())));
		MvcResult mvcResult = mockMvc.perform(get("/api/v1/products/13860416/").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();
		String resultString = mvcResult.getResponse().getContentAsString();
		objMapper = new ObjectMapper();
		ProductPrice result = objMapper.readValue(resultString, ProductPrice.class);
		assertEquals(result.getProductId(), 13860416);
		assertEquals(result.getProductName(), "Progressive power yoga:Sedona experie (DVD)");
		assertEquals(result.getPrice().getValue(), BigDecimal.valueOf(1000));
		assertEquals(result.getPrice().getCurrencyCode(), Currency.getInstance(Locale.getDefault()).getCurrencyCode());
	}

	/**
	 * Validating the getProduct Request for the invalid product ID
	 * 
	 * @throws Exception
	 */
	@Test
	public void getProductsTest_ForInValidProduct() throws Exception {
		when(productService.getProductInfo("13860422")).thenReturn(new ProductPrice(13860416,
				"Progressive power yoga:Sedona experie (DVD)",
				new Price(BigDecimal.valueOf(1000), Currency.getInstance(Locale.getDefault()).getCurrencyCode())));
		mockMvc.perform(get("/api/v1/products/13860422/").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();
	}

	/**
	 * Validating the product price update with valid product id in the request
	 * 
	 * @throws Exception
	 */
	@Test
	public void updateProductInfoTest_ForValidProductID() throws Exception {
		ProductPrice productPrice = new ProductPrice(13860416,
				new Price(BigDecimal.valueOf(2000), Currency.getInstance(Locale.getDefault()).getCurrencyCode()));
		priceJson = objMapper.writeValueAsString(productPrice);
		MockHttpServletResponse response = mockMvc
				.perform(put("/api/v1/products/13860416/").contentType(MediaType.APPLICATION_JSON).content(priceJson))
				.andExpect(status().is2xxSuccessful()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andReturn().getResponse();
		ResponseInfo result = objMapper.readValue(response.getContentAsString(), ResponseInfo.class);
		assertEquals(result.getCode(), HttpStatus.OK.getReasonPhrase());
		assertEquals(result.getMessage(), MessageConstants.PRODUCT_UPDATE_SUCCESS);
		assertEquals(result.getResponseType(), MessageConstants.RESPONSE_TYPE_SUCCESS);
	}

	/**
	 * Validating the product price update with invalid product id in the request
	 * 
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void updateProductInfoTest_ForInValidProductID() throws Exception {
		ProductPrice productPrice = new ProductPrice(13860422,
				new Price(BigDecimal.valueOf(2000), Currency.getInstance(Locale.getDefault()).getCurrencyCode()));
		priceJson = objMapper.writeValueAsString(productPrice);
		MockHttpServletResponse response = mockMvc
				.perform(put("/api/v1/products/123456/").contentType(MediaType.APPLICATION_JSON).content(priceJson))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andReturn().getResponse();
		ResponseInfo result = objMapper.readValue(response.getContentAsString(), ResponseInfo.class);
		assertEquals(result.getCode(), MessageConstants.MISSING_PRODUCT_PRICE_ERROR_CODE);
		assertEquals(result.getMessage(), MessageConstants.MISSING_PRODUCT_PRICE_ERROR_DESCRIPTION);
		assertEquals(result.getResponseType(), MessageConstants.RESPONSE_TYPE_ERROR);
	}

	/**
	 * Validating the product price update with valid currency code in the request
	 * 
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void updateProductInfoTest_WithInValidCurrencyCode() throws Exception {
		ProductPrice productPrice = new ProductPrice(13860416, new Price(BigDecimal.valueOf(2000), "ABCD"));
		priceJson = objMapper.writeValueAsString(productPrice);
		mockMvc.perform(put("/api/v1/products/13860416/").contentType(MediaType.APPLICATION_JSON).content(priceJson))
				.andExpect(status().is4xxClientError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn().getResponse();
	}

	/**
	 * Validating the product price update with no currency code information in the
	 * request
	 * 
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void updateProductInfoTest_WithBlankCurrencyCode() throws Exception {
		ProductPrice productPrice = new ProductPrice(13860416, new Price(BigDecimal.valueOf(2000), null));
		priceJson = objMapper.writeValueAsString(productPrice);
		mockMvc.perform(put("/api/v1/products/13860416/").contentType(MediaType.APPLICATION_JSON).content(priceJson))
				.andExpect(status().is4xxClientError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn().getResponse();
	}

	/**
	 * Validating the product price update with invalid price information in the
	 * request
	 * 
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void updateProductInfoTest_WithInValidPrice() throws Exception {
		ProductPrice productPrice = new ProductPrice(13860416,
				new Price(BigDecimal.valueOf(-1), Currency.getInstance(Locale.getDefault()).getCurrencyCode()));
		MockHttpServletResponse response = mockMvc
				.perform(put("/api/v1/products/13860416/").contentType(MediaType.APPLICATION_JSON)
						.content(objMapper.writeValueAsString(productPrice)))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andReturn().getResponse();
		ResponseInfo result = objMapper.readValue(response.getContentAsString(), ResponseInfo.class);
		assertEquals(result.getCode(), MessageConstants.INVALID_PRODUCT_PRICE_ERROR_CODE);
		assertEquals(result.getMessage(), MessageConstants.INVALID_PRODUCT_PRICE_ERROR_DESCRIPTION);
		assertEquals(result.getResponseType(), MessageConstants.RESPONSE_TYPE_ERROR);
	}

	/**
	 * Validating the product price update with no price information in the request
	 * 
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void updateProductInfoTest_WithNoPrice() throws Exception {
		ProductPrice productPrice = new ProductPrice(13860416, null);
		MockHttpServletResponse response = mockMvc
				.perform(put("/api/v1/products/13860416/").contentType(MediaType.APPLICATION_JSON)
						.content(objMapper.writeValueAsString(productPrice)))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andReturn().getResponse();
		ResponseInfo result = objMapper.readValue(response.getContentAsString(), ResponseInfo.class);
		assertEquals(result.getCode(), MessageConstants.MISSING_PRODUCT_PRICE_ERROR_CODE);
		assertEquals(result.getMessage(), MessageConstants.MISSING_PRODUCT_PRICE_ERROR_DESCRIPTION);
		assertEquals(result.getResponseType(), MessageConstants.RESPONSE_TYPE_ERROR);
	}

	/**
	 * Validating the product Id mismatch between the URI and Request Body
	 * 
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void updateProductInfoTest_URIandBodyProductId_Mismatch() throws Exception {
		MockHttpServletResponse response = mockMvc
				.perform(put("/api/v1/products/13860422/").contentType(MediaType.APPLICATION_JSON).content(priceJson))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andReturn().getResponse();
		ResponseInfo result = objMapper.readValue(response.getContentAsString(), ResponseInfo.class);
		assertEquals(result.getCode(), MessageConstants.MISMATCH_PRODUCT_ID_ERROR_CODE);
		assertEquals(result.getMessage(), MessageConstants.MISMATCH_PRODUCT_ID_ERROR_DESCRIPTION);
		assertEquals(result.getResponseType(), MessageConstants.RESPONSE_TYPE_ERROR);
	}

}
