package com.tgt.retail.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.RedirectStrategy;

/*
 * Overriding Spring Security's redirect strategy to avoid redirecting to form based login
 */
public class NoRedirectStrategy implements RedirectStrategy {

	@Override
	public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
		// There is no need to implement as there will be no redirection required for REST service authentication.
	}

}
