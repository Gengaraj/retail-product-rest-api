package com.tgt.retail.api.product.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tgt.retail.api.product.vo.ProductPrice;

/*
 * Repository to retrieve the product price information from Mongo DB, implementation will be provided by Spring Data
 */
@Repository
public interface ProductRepository extends MongoRepository<ProductPrice, Long> {

	/**
	 * Method to find the Price information from MongoDB for the given Id
	 * 
	 * @param productId
	 * @return
	 */
	public ProductPrice findProductPriceByProductId(long productId);

}
