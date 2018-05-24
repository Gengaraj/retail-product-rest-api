package com.tgt.retail.api.product.service;

import com.tgt.retail.api.product.vo.ProductPrice;

/*
 * Service Layer interface to define the operation should be implemented to interact with Mongo DB to get and update Product Price details
 */
public interface ProductService {

	/**
	 * To retrieve the price information DB for the given productId
	 * 
	 * @param id
	 * @return
	 */
	public ProductPrice findProductPriceByProductId(long id);

	/**
	 * To get the product info from redsky product endpoint to get more info about
	 * the product
	 * 
	 * @param productId
	 * @return
	 */
	public ProductPrice getProductInfo(String productId);

	/**
	 * To update the price information in the DB for the given product
	 * 
	 * @param product
	 */
	public void updateProductPrice(ProductPrice product);

}
