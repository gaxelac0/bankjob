package com.tpo.bankjob.model.exception;

@SuppressWarnings("serial")
public class BadFormatException extends RuntimeException {
	
	public BadFormatException() {
		super("El formato del parámetro debe ser mmYYYY. Ejemplo: 112021 para Noviembre 2021.");
	}
}
