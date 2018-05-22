package com.tgt.retail.api.product.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseInfo {

	@JsonProperty("Code")
	private String code;

	@JsonProperty("Message")
	private String message;

	@JsonProperty("Response Type")
	private String responseType;
	
	public ResponseInfo() {
	}

	public ResponseInfo(String code, String message, String responseType) {
		this.code = code;
		this.message = message;
		this.responseType = responseType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

}
