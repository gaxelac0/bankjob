package com.tpo.bankjob.model.exception;

@SuppressWarnings("serial")
public class PublicacionNotFoundException extends RuntimeException {
	
	public PublicacionNotFoundException(String id) {
		super("No se pudo encontrar la publicacion ID("+id+").");
	}
}
