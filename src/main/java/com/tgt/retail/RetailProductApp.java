package com.tgt.retail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * Entry point of the application using enabling auto configuration and Component Scanning 
 */
@SpringBootApplication
public class RetailProductApp {

	public static void main(String[] args) {
		SpringApplication.run(RetailProductApp.class, args);
	}
}
