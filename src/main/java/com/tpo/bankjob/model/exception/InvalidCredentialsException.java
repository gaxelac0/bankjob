package com.tpo.bankjob.model.exception;

@SuppressWarnings("serial")
public class InvalidCredentialsException extends RuntimeException {
	
	public InvalidCredentialsException() {
		super("Credenciales invalidas.");
	}

}
