package com.tgt.retail.api.product.validator;

import java.math.BigDecimal;
import java.util.Currency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tgt.retail.api.product.constants.MessageConstants;
import com.tgt.retail.api.product.exception.InvalidInputException;
import com.tgt.retail.api.product.vo.ProductPrice;
import com.tgt.retail.api.product.vo.ResponseInfo;

/*
 * Validator class to perform the validation against the input data
 */
@Component
public class ProductValidator implements Validator {

	Logger logger = LoggerFactory.getLogger(ProductValidator.class);

	/**
	 * Validate whether the given product is the valid or not
	 * 
	 * @param productId
	 * @return
	 */
	public boolean isValidProductId(String productId) {
		boolean valid = true;
		try {
			Long.parseLong(productId);
		} catch (NumberFormatException | NullPointerException e) {
			valid = false;
			logger.warn("Invalid Product Id");
			throw new InvalidInputException(new ResponseInfo(MessageConstants.INVALID_PRODUCT_ID_ERROR_CODE,
					MessageConstants.INVALID_PRODUCT_ID_ERROR_DESCRIPTION, MessageConstants.RESPONSE_TYPE_ERROR));
		}
		return valid;
	}

	/**
	 * Validates whether the productID given in the URI matches with the one
	 * provided in the request body
	 * 
	 * @param product
	 * @param productId
	 * @return
	 */
	public boolean isValidProductMatch(ProductPrice product, String productId) {
		boolean valid = true;
		if (!isValidProductId(productId) || Long.parseLong(productId) != product.getProductId()) {
			valid = false;
			throw new InvalidInputException(new ResponseInfo(MessageConstants.MISMATCH_PRODUCT_ID_ERROR_CODE,
					MessageConstants.MISMATCH_PRODUCT_ID_ERROR_DESCRIPTION, MessageConstants.RESPONSE_TYPE_ERROR));
		}
		return valid;
	}

	/**
	 * validates whether the given price is valid or not
	 * 
	 * @param price
	 * @return
	 */
	public boolean isValidProductPrice(BigDecimal price) {
		boolean valid = true;
		if (price == null || price.doubleValue() <= 0.0) {
			valid = false;
		}
		return valid;
	}

	/**
	 * Validates the currency Code against ISO 4217 currency code list
	 * 
	 * @param currencyCode
	 * @return
	 */
	public boolean isValidCurrencyCode(String currencyCode) {
		boolean valid = true;
		try {
			Currency.getInstance(currencyCode);
		} catch (IllegalArgumentException illegalArgumentException) {
			valid = false;
		}
		return valid;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return ProductPrice.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ProductPrice productPrice = (ProductPrice) target;
		if (productPrice.getPrice() == null) {
			logger.warn(MessageConstants.MISSING_PRODUCT_PRICE_ERROR_CODE);
			throw new InvalidInputException(new ResponseInfo(MessageConstants.MISSING_PRODUCT_PRICE_ERROR_CODE,
					MessageConstants.MISSING_PRODUCT_PRICE_ERROR_DESCRIPTION, MessageConstants.RESPONSE_TYPE_ERROR));
		} else if (!this.isValidProductPrice(productPrice.getPrice().getValue())) {
			logger.warn(MessageConstants.INVALID_PRODUCT_PRICE_ERROR_DESCRIPTION);
			throw new InvalidInputException(new ResponseInfo(MessageConstants.INVALID_PRODUCT_PRICE_ERROR_CODE,
					MessageConstants.INVALID_PRODUCT_PRICE_ERROR_DESCRIPTION, MessageConstants.RESPONSE_TYPE_ERROR));
		} else if (productPrice.getPrice().getCurrencyCode() == null
				|| productPrice.getPrice().getCurrencyCode().trim().equals("")) {
			logger.warn(MessageConstants.MISSING_CURRENCY_ERROR_CODE);
			throw new InvalidInputException(new ResponseInfo(MessageConstants.MISSING_CURRENCY_ERROR_CODE,
					MessageConstants.MISSING_CURRENCY_ERROR_DESCRIPTION, MessageConstants.RESPONSE_TYPE_ERROR));
		} else if (!isValidCurrencyCode(productPrice.getPrice().getCurrencyCode().trim())) {
			logger.warn(MessageConstants.INVALID_CURRENCY_ERROR_CODE);
			throw new InvalidInputException(new ResponseInfo(MessageConstants.INVALID_CURRENCY_ERROR_CODE,
					MessageConstants.INVALID_CURRENCY_ERROR_DESCRIPTION, MessageConstants.RESPONSE_TYPE_ERROR));
		}
	}
}
