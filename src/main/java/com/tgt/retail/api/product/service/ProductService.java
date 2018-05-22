package com.tgt.retail.api.product.service;

import com.tgt.retail.api.product.vo.ProductPrice;

public interface ProductService {
	
	public ProductPrice findProductPriceByProductId(long id);
	
	public ProductPrice getProductInfo(String productId);
	
	public void updateProductPrice(ProductPrice product);

}
