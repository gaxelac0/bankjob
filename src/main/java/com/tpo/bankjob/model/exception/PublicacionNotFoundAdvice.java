package com.tpo.bankjob.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class PublicacionNotFoundAdvice {	

	@ResponseBody
	@ExceptionHandler(PublicacionNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String publicacionNotFoundHandler(PublicacionNotFoundException ex) {
		return ex.getMessage();
	}
}