package com.tpo.bankjob.model.exception;

import com.tpo.bankjob.model.vo.UserType;

@SuppressWarnings("serial")
public class InvalidUserTypeException extends RuntimeException {
	
	public InvalidUserTypeException(UserType type) {
	    super("El tipo de usuario(" + type + ") es invalido.");
	  }
}