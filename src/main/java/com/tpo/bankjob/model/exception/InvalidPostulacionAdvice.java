package com.tpo.bankjob.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class InvalidPostulacionAdvice {	

	@ResponseBody
	@ExceptionHandler(InvalidPostulacionException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String invalidPostulacionHandler(InvalidPostulacionException ex) {
		return ex.getMessage();
	}
}