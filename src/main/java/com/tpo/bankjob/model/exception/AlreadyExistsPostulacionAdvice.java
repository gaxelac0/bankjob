package com.tpo.bankjob.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class AlreadyExistsPostulacionAdvice {	

	@ResponseBody
	@ExceptionHandler(AlreadyExistsPostulacionException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String alreadyExistsPostulacionHandler(AlreadyExistsPostulacionException ex) {
		return ex.getMessage();
	}
}