package com.tpo.bankjob.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class InvalidActionAdvice {	

	@ResponseBody
	@ExceptionHandler(InvalidActionException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	String invalidActionHandler(InvalidActionException ex) {
		return ex.getMessage();
	}
}