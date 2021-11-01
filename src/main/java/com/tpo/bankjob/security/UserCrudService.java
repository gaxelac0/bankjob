package com.tpo.bankjob.security;

import java.util.Optional;

public interface UserCrudService {

	Object save(String id, Object user);

	Optional<Object> find(String id);

	Optional<Object> findByUsername(String username);
}