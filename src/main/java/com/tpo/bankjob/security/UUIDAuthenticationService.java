package com.tpo.bankjob.security;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class UUIDAuthenticationService implements UserAuthenticationService {

	@NonNull
	UserCrudService users;

	@Override
	public Optional<String> login(final String username, final String password) {
		final String uuid = UUID.randomUUID().toString();
		final User user = new User(uuid, username, password);

		users.save(uuid, user);
		return Optional.of(uuid);
	}

	@Override
	public Optional<Object> findByToken(final String token) {
		return users.find(token);
	}
	
	@Override
	public Optional<Object> findByUsername(final String username) {
		// TODO. implement user repository
		return null;
	}

	@Override
	public void logout(final Object user) {

	}
}