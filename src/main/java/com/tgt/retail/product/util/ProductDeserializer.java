package com.tgt.retail.product.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.tgt.retail.api.product.vo.Product;

/*
 * Custom Product Deserializer to extract only the required data such as id and product name from redsky product endpoint response
 */
public class ProductDeserializer extends JsonDeserializer<Product> {

	@Override
	public Product deserialize(JsonParser parser, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		JsonNode node = parser.getCodec().readTree(parser);
		Long id = Long.parseLong((node.get("product").get("available_to_promise_network").get("product_id")).asText());
		String productName = node.get("product").get("item").get("product_description").get("title").asText();
		return new Product(id, productName);
	}
}
