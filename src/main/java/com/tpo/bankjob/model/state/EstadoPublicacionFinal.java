package com.tpo.bankjob.model.state;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tpo.bankjob.model.Publicacion;
import com.tpo.bankjob.model.exception.InvalidActionException;

public class EstadoPublicacionFinal extends EstadoPublicacion {

	private static final long serialVersionUID = 2173528406437960326L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EstadoPublicacionFinal.class);

	public EstadoPublicacionFinal(Publicacion ctx) {
		super(ctx);
	}

	public Publicacion transicionar(Publicacion ctx) {
		LOGGER.info("No se llegara a esta instancia nunca. "
				+ "Irreversible. "
				+ "La publicacion ya se encuentra finalizada.");
		return ctx;
	}

	@Override
	public void open(Publicacion ctx) {
		throw new InvalidActionException("No se pueden abrir publicaciones "
				+ "que se encuentren finalizadas.");
	}
}
