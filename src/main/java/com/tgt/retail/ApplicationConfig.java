package com.tgt.retail;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.tgt.retail.api.product.repository.ProductRepository;
import com.tgt.retail.api.product.vo.Price;
import com.tgt.retail.api.product.vo.ProductPrice;

@Configuration
public class ApplicationConfig {
	
	private final Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);

	@Autowired
	private ProductRepository productRepository;
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.setConnectTimeout(1000).setReadTimeout(1000).build();
	}
	
	@Bean
	CommandLineRunner runner() {
		logger.info("Prepopulating mongo db for product price values");
		return args -> {
			productRepository.save(new ProductPrice(13860416,
					new Price(BigDecimal.valueOf(1000), Currency.getInstance(Locale.getDefault()).getCurrencyCode())));
			productRepository.save(new ProductPrice(13860418,
					new Price(BigDecimal.valueOf(2000), Currency.getInstance(Locale.getDefault()).getCurrencyCode())));
			productRepository.save(new ProductPrice(13860420,
					new Price(BigDecimal.valueOf(3000), Currency.getInstance(Locale.getDefault()).getCurrencyCode())));
			productRepository.save(new ProductPrice(13860421,
					new Price(BigDecimal.valueOf(4000), Currency.getInstance(Locale.getDefault()).getCurrencyCode())));
			productRepository.save(new ProductPrice(13860424,
					new Price(BigDecimal.valueOf(5000), Currency.getInstance(Locale.getDefault()).getCurrencyCode())));
			productRepository.save(new ProductPrice(13860425,
					new Price(BigDecimal.valueOf(6000), Currency.getInstance(Locale.getDefault()).getCurrencyCode())));
			productRepository.save(new ProductPrice(13860428,
					new Price(BigDecimal.valueOf(7000), Currency.getInstance(Locale.getDefault()).getCurrencyCode())));
			productRepository.save(new ProductPrice(13860429,
					new Price(BigDecimal.valueOf(8000), Currency.getInstance(Locale.getDefault()).getCurrencyCode())));
			productRepository.save(new ProductPrice(13860432,
					new Price(BigDecimal.valueOf(9000), Currency.getInstance(Locale.getDefault()).getCurrencyCode())));
			productRepository.save(new ProductPrice(13860433,
					new Price(BigDecimal.valueOf(10000), Currency.getInstance(Locale.getDefault()).getCurrencyCode())));
			/*productRepository.save(new ProductPrice(16696652,
					new Price(BigDecimal.valueOf(11000), Currency.getInstance(Locale.getDefault()).getCurrencyCode())));*/
		};
	}

}
