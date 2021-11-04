package com.tpo.bankjob.model.exception;

@SuppressWarnings("serial")
public class InsufficientSkillsForPostulacionException extends RuntimeException {
	
	public InsufficientSkillsForPostulacionException() {
		super("El postulante no cumple con los requisitos de la publicacion.");
	}
}
