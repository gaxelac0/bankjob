package com.tpo.bankjob.security;

import static java.util.Optional.ofNullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
final class InMemoryUsers implements UserCrudService {

	Map<String, UserDetails> users = new HashMap<>();

	@Override
	public UserDetails save(String id, UserDetails user) {
		return users.put(id, user);
	}

	@Override
	public Optional<UserDetails> find(final String id) {
		return ofNullable(users.get(id));
	}

	@Override
	public Optional<UserDetails> findByUsername(String username) {
		
		return users.entrySet().stream()
		.filter((a) -> a.getValue().getUsername().equalsIgnoreCase(username))
		.findAny()
		.map((t) -> t.getValue());
	}

//	@Override
//	public Optional<Object> findByUsername(final String username) {
//		return users
//				.values()
//				.stream()
//				.filter(u -> Objects.equals(username, u.getUsername()))
//				.findFirst();
//	}
}