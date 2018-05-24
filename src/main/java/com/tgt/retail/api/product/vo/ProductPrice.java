package com.tgt.retail.api.product.vo;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * Value object to hold the details about product and associated product information
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "productprice")
public class ProductPrice {

	@Id
	@JsonProperty("id")
	private long productId;

	@Transient
	@JsonProperty("name")
	private String productName;

	@JsonProperty("current_price")
	private Price price;

	public ProductPrice() {
	}

	public ProductPrice(long productId, String productName, Price price) {
		this.productId = productId;
		this.productName = productName;
		this.price = price;
	}

	public ProductPrice(long productId, Price price) {
		this.productId = productId;
		this.price = price;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "product id : " + this.productId + ", current price : " + this.price.toString();
	}

}
