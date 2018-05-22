package com.tgt.retail.api.product.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

import com.tgt.retail.api.product.constants.MessageConstants;
import com.tgt.retail.api.product.exception.InvalidInputException;
import com.tgt.retail.api.product.exception.ProductNotFoundException;
import com.tgt.retail.api.product.vo.ResponseInfo;

@RestControllerAdvice
public class ProductControllerAdvice {
	
	Logger logger = LoggerFactory.getLogger(ProductControllerAdvice.class);
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseInfo invalidJsonMessage(
			HttpMessageNotReadableException e) {
		return new ResponseInfo(HttpStatus.BAD_REQUEST.getReasonPhrase(),e.getMessage(), MessageConstants.RESPONSE_TYPE_ERROR);
	}
	
	@ExceptionHandler(InvalidInputException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseInfo invalidInput(
			InvalidInputException e) {
	  return e.getError();
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseInfo productNotFound(
			ProductNotFoundException e) {
	  long productId = e.getProductId();
	  return new ResponseInfo(HttpStatus.NOT_FOUND.getReasonPhrase(), String.format(MessageConstants.PRODUCT_NOT_FOUND, productId), MessageConstants.RESPONSE_TYPE_ERROR);
	}
	
	@ExceptionHandler(ResourceAccessException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseInfo productServiceNotReachable(
			Exception e) {
	  logger.error(MessageConstants.PRODUCT_END_POINT_UNREACHABLE);
	  return new ResponseInfo(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), MessageConstants.PRODUCT_END_POINT_UNREACHABLE, MessageConstants.RESPONSE_TYPE_ERROR);
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String serverError(
			Exception e) {
		e.printStackTrace();
	  return e.getMessage();
	}
}
