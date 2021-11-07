package com.tpo.bankjob.model.exception;

@SuppressWarnings("serial")
public class InvalidActionException extends RuntimeException {
	
	public InvalidActionException(String what) {
		super(what);
	}
}
