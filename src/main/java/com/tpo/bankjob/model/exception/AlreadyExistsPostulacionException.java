package com.tpo.bankjob.model.exception;

import com.tpo.bankjob.model.utils.PostulacionKeyWrapper;

public class AlreadyExistsPostulacionException extends RuntimeException {
	
	private static final long serialVersionUID = -1972517040513761485L;

	public AlreadyExistsPostulacionException(PostulacionKeyWrapper postulacion) {
	    super("Ya existe una postulacion del postulante ID(" + postulacion.getIdPostulante() +
	    		") para la publicacion ID (" + postulacion.getIdPublicacion() + ").");
	  }

}
