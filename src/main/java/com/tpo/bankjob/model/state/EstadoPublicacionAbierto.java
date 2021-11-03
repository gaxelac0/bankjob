package com.tpo.bankjob.model.state;

import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tpo.bankjob.conf.GlobalProperties;
import com.tpo.bankjob.model.vo.PublicacionVO;

@Component
public class EstadoPublicacionAbierto extends EstadoPublicacion {

	private static final long serialVersionUID = 1940787522775270537L;
	private static final Logger LOGGER = LoggerFactory.getLogger(EstadoPublicacionAbierto.class);

	//@Autowired
	//@JsonIgnore
   // public GlobalProperties properties;

	public EstadoPublicacionAbierto(PublicacionVO ctx) {
		super(ctx);
	}

	public PublicacionVO transicionar(PublicacionVO ctx) {
		if(Instant.now().toDateTime().isAfter(ctx.getFechaVigencia())) {
			ctx.setEstado(new EstadoPublicacionCerrado(ctx));			
			ctx.setFechaVigencia(new DateTime().plusDays(/*properties.getDias()*/14)); // TODO fix propeties coming null
			LOGGER.info("Publicacion ID(" + ctx.getId() + ") cerrada automaticamente por cumplir tiempo de vigencia abierto.");
		}
		
		return ctx;
	}

}
