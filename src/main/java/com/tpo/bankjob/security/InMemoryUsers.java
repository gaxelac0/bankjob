package com.tpo.bankjob.security;

import static java.util.Optional.ofNullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
final class InMemoryUsers implements UserCrudService {

	Map<String, Object> users = new HashMap<>();

	@Override
	public Object save(String id, Object user) {
		return users.put(id, user);
	}

	@Override
	public Optional<Object> find(final String id) {
		return ofNullable(users.get(id));
	}

	@Override
	public Optional<Object> findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
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