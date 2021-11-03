package com.tpo.bankjob.model.exception;

@SuppressWarnings("serial")
public class PostulanteNotFoundException extends RuntimeException {
	
	public PostulanteNotFoundException(String string) {
		super("No se pudo encontrar el postulante ID("+string+").");
	}
}
