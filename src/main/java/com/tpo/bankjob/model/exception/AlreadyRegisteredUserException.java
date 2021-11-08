package com.tpo.bankjob.model.exception;

public class AlreadyRegisteredUserException extends RuntimeException {
	
	private static final long serialVersionUID = 936263084007172789L;

	public AlreadyRegisteredUserException(String username) {
	    super("Ya existe un usuario registrado con el usuario informado: "+username);
	  }

}
