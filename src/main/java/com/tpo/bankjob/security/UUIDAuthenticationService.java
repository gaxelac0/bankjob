package com.tpo.bankjob.security;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.tpo.bankjob.model.exception.InvalidCredentialsException;

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
	public UserDetails login(final String username, final String password) 
			throws InvalidCredentialsException {
		
		Optional<UserDetails> ret = users.findByUsername(username);
		if(!ret.isPresent() || !ret.get().getPassword().equalsIgnoreCase(password)) {
			throw new InvalidCredentialsException(); // TODO: exception
		}
		
		return ret.get();
	}

	@Override
	public Optional<UserDetails> findByToken(final String token) {
		return users.find(token);
	}

	@Override
	public void logout(final Object user) {

	}
}