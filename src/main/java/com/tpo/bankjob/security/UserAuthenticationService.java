package com.tpo.bankjob.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserAuthenticationService {

	/**
	 * Logs in with the given {@code username} and {@code password}.
	 *
	 * @param username
	 * @param password
	 * @return user {@link token} when login succeeds
	 */
	UserDetails login(String username, String password);

	/**
	 * Finds a user by its dao-key.
	 *
	 * @param token user dao key
	 * @return
	 */
	Optional<UserDetails> findByToken(String token);

	/**
	 * Logs out the given input {@code user}.
	 *
	 * @param user the user to logout
	 */
	void logout(Object user);
}