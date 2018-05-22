package com.tgt.retail.api.product.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tgt.retail.api.product.vo.ProductPrice;

@Repository
public interface ProductRepository extends MongoRepository<ProductPrice, Long>{
	
	public ProductPrice findProductPriceByProductId(long productId);
	
}
