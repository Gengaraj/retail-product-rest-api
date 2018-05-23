package com.tgt.retail.auth.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.tgt.retail.auth.vo.User;

@Service
public class SimpleAuthenticationService implements UserAuthenticationService {

	public static Map<String, User> users = new HashMap<>();

	@Override
	public Optional<String> login(String username, String password) {
		final String token = UUID.randomUUID().toString();
		final User user = User.builder().id(token).username(username).password(password).build();
		users.put(token, user);
		return Optional.of(token);
	}

	@Override
	public Optional<User> findByToken(String token) {
		return Optional.ofNullable(users.get(token));
	}

	@Override
	public void logout(User user) {
		users.remove(user.getId());
	}

}
