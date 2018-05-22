package com.tgt.retail.api.product.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tgt.retail.api.product.constants.MessageConstants;
import com.tgt.retail.api.product.service.ProductService;
import com.tgt.retail.api.product.validator.ProductValidator;
import com.tgt.retail.api.product.vo.ProductPrice;
import com.tgt.retail.api.product.vo.ResponseInfo;

@RestController
@RequestMapping("/api")
public class ProductController {

	Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	ProductValidator productValidator;

	public ProductValidator getProductValidator() {
		return productValidator;
	}

	public void setProductValidator(ProductValidator productValidator) {
		this.productValidator = productValidator;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	@Autowired
	ProductService productService;

	@InitBinder("productPrice")
	public void setProductsBinder(WebDataBinder webDataBinder) {
		webDataBinder.setValidator(productValidator);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(value = "/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductPrice getProductById(@PathVariable("id") String productId) {
		ProductPrice productPrice = null;
		if (productValidator.isValidProductId(productId)) {
			productPrice = productService.getProductInfo(productId);
		}
		return productPrice;
	}

	@PutMapping(value = "/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseInfo> updateProdcutPrice(@Valid @RequestBody ProductPrice productPrice,
			@PathVariable("id") String productId) {
		if (productValidator.isValidProductMatch(productPrice, productId)) {
			productService.updateProductPrice(productPrice);
		}
		return ResponseEntity.ok(new ResponseInfo(HttpStatus.OK.getReasonPhrase(),
				MessageConstants.PRODUCT_UPDATE_SUCCESS, MessageConstants.RESPONSE_TYPE_SUCCESS));
	}

}
