package com.tpo.bankjob.model.exception;

public class AlreadyRegisteredUserException extends RuntimeException {
	
	public AlreadyRegisteredUserException(String username) {
	    super("Ya existe un usuario registrado con el usuario informado: "+username);
	  }

}
