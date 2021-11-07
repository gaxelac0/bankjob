package com.tpo.bankjob.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class BadFormatAdvice {	

	@ResponseBody
	@ExceptionHandler(BadFormatException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String badFormatHandler(BadFormatException ex) {
		return ex.getMessage();
	}
}