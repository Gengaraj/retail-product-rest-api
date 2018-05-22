package com.tgt.retail.api.product.exception;

public class ProductNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 4131015822216928991L;
	
	private long productId;
	
	public ProductNotFoundException(long productId) {
		this.productId = productId;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}
	
}
