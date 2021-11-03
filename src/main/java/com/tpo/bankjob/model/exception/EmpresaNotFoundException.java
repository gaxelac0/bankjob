package com.tpo.bankjob.model.exception;

@SuppressWarnings("serial")
public class EmpresaNotFoundException extends RuntimeException {

	public EmpresaNotFoundException(String id) {
		super("No se pudo encontrar la empresa ID("+id+").");
	}
}
