package com.tpo.bankjob.model.state;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tpo.bankjob.model.vo.PublicacionVO;

public class EstadoPublicacionCerrado extends EstadoPublicacion {

	private static final long serialVersionUID = 4067463337202370244L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EstadoPublicacionCerrado.class);
		
	public EstadoPublicacionCerrado(PublicacionVO ctx) {
		super(ctx);
	}

	public PublicacionVO transicionar(PublicacionVO ctx) {
		if(new DateTime().isAfter(ctx.getFechaVigencia())) {
			ctx.setEstado(new EstadoPublicacionFinal(ctx));
			LOGGER.info("Publicacion ID(" + ctx.getId() + ") finalizada automaticamente cumplir tiempo de vigencia cerrado.");
		}
		
		return ctx;
	}

}
