package com.tpo.bankjob.model.state;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tpo.bankjob.model.vo.PublicacionVO;

public class EstadoPublicacionAbierto extends EstadoPublicacion {

	private static final long serialVersionUID = 1940787522775270537L;
	private static final Logger LOGGER = LoggerFactory.getLogger(EstadoPublicacionAbierto.class);

	public EstadoPublicacionAbierto(PublicacionVO ctx) {
		super(ctx);
	}

	public PublicacionVO transicionar(PublicacionVO ctx) {
		if(new DateTime().isAfter(ctx.getFechaVigencia())) {
			ctx.setEstado(new EstadoPublicacionCerrado(ctx));			
			ctx.setFechaVigencia(new DateTime().plusDays(1)); // TODO  configurar fecha de vigencia cerrada en properties
			LOGGER.info("Publicacion ID(" + ctx.getId() + ") cerrada automaticamente por cumplir tiempo de vigencia abierto.");
		}
		
		return ctx;
	}

}
