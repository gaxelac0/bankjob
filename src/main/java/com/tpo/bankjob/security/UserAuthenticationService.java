package com.tpo.bankjob.security;

import java.util.Optional;

public interface UserAuthenticationService {

	/**
	 * Logs in with the given {@code username} and {@code password}.
	 *
	 * @param username
	 * @param password
	 * @return an {@link Optional} of a user when login succeeds
	 */
	Optional<String> login(String username, String password);

	/**
	 * Finds a user by its dao-key.
	 *
	 * @param token user dao key
	 * @return
	 */
	Optional<Object> findByToken(String token);
	
	/**
	 * Finds a user by its username
	 *
	 * @param username username
	 * @return
	 */
	Optional<Object> findByUsername(String username);

	/**
	 * Logs out the given input {@code user}.
	 *
	 * @param user the user to logout
	 */
	void logout(Object user);
}