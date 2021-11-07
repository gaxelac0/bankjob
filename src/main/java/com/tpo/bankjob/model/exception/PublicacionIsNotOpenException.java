package com.tpo.bankjob.model.exception;

@SuppressWarnings("serial")
public class PublicacionIsNotOpenException extends RuntimeException {
	
	public PublicacionIsNotOpenException() {
		super("La publicacion no se encuentra abierta a postulaciones.");
	}
}
