package com.tpo.bankjob.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class AlreadyRegisteredUserAdvicer {	

	@ResponseBody
	@ExceptionHandler(AlreadyRegisteredUserException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String alreadyRegisteredUserHandler(AlreadyRegisteredUserException ex) {
		return ex.getMessage();
	}
}