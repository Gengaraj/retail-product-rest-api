package com.tgt.retail.auth.controller;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tgt.retail.auth.service.UserAuthenticationService;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

/*
 *Login Controller to handle the authentication request  
 */
@RestController
@RequestMapping("/auth/")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class LoginController {

	@NonNull
	UserAuthenticationService authentication;

	/**
	 * Handler method to handle authentication request and provide Bearer Token back
	 * in the response
	 * 
	 * @param request
	 * @param username
	 * @param password
	 * @return
	 */
	@PostMapping("/login")
	String login(final HttpServletRequest request, @RequestParam("username") final String username,
			@RequestParam("password") final String password) {
		return authentication.login(username, password)
				.orElseThrow(() -> new RuntimeException("invalid login and/or password"));
	}
}
