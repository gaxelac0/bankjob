package com.tpo.bankjob.model.exception;

@SuppressWarnings("serial")
public class EmpresaNotFoundException extends RuntimeException {
	
	public EmpresaNotFoundException(Long id) {
	    super("No se pudo encontrar la empresa ID("+id+").");
	  }
}
