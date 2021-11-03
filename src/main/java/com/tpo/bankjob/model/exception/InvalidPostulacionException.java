package com.tpo.bankjob.model.exception;

@SuppressWarnings("serial")
public class InvalidPostulacionException extends RuntimeException {
	
	public InvalidPostulacionException(String str) {
		super(str);
	}
}
