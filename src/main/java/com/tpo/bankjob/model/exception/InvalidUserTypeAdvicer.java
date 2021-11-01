package com.tpo.bankjob.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class InvalidUserTypeAdvicer {	

	@ResponseBody
	@ExceptionHandler(InvalidUserTypeException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String invalidUserTypeHandler(InvalidUserTypeException ex) {
		return ex.getMessage();
	}
}