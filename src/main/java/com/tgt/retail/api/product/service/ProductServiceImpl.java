package com.tgt.retail.api.product.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tgt.retail.api.product.repository.ProductRepository;
import com.tgt.retail.api.product.vo.Price;
import com.tgt.retail.api.product.vo.ProductPrice;

/*
 * Service Implementation to connect with redsky endpoint through Prodcut Client wrapper to receive product information and perfrom CRUD operations on the Product Price collection in Mongo DB. 
 */
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductClient productClient;

	@Override
	public ProductPrice findProductPriceByProductId(long id) {
		return productRepository.findProductPriceByProductId(id);
	}

	@Override
	public ProductPrice getProductInfo(String productId) {
		ProductPrice productPrice = null;
		long id = Long.parseLong(productId);
		String name = productClient.getProductNameById(id);
		if (StringUtils.isNotBlank(name)) {
			productPrice = this.findProductPriceByProductId(id);
			if (productPrice == null) {
				productPrice = new ProductPrice();
				productPrice.setPrice(new Price());
			}
			productPrice.setProductId(id);
			productPrice.setProductName(name);
		}
		return productPrice;
	}

	@Override
	public void updateProductPrice(ProductPrice productPrice) {
		productClient.getProductNameById(productPrice.getProductId());
		productRepository.save(new ProductPrice(productPrice.getProductId(), productPrice.getPrice()));
	}

	public ProductRepository getProductRepository() {
		return productRepository;
	}

	public void setProductRepository(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

}
