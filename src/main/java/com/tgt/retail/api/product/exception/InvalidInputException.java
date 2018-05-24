package com.tgt.retail.api.product.exception;

import com.tgt.retail.api.product.vo.ResponseInfo;

/*
 * Exception to hold more information about the error after the input validation 
 */
public class InvalidInputException extends RuntimeException {

	private static final long serialVersionUID = -6068253766374329097L;

	private ResponseInfo error;

	public InvalidInputException(ResponseInfo error) {
		this.error = error;
	}

	public ResponseInfo getError() {
		return error;
	}

	public void setError(ResponseInfo error) {
		this.error = error;
	}

}
