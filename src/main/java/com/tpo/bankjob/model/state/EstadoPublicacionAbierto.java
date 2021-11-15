package com.tpo.bankjob.model.state;

import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.tpo.bankjob.model.Publicacion;
import com.tpo.bankjob.model.exception.InvalidActionException;

@Component
public class EstadoPublicacionAbierto extends EstadoPublicacion {

	private static final long serialVersionUID = 1940787522775270537L;
	private static final Logger LOGGER = LoggerFactory.getLogger(EstadoPublicacionAbierto.class);

	//@Autowired
	//@JsonIgnore
   // public GlobalProperties properties;

	public EstadoPublicacionAbierto(Publicacion ctx) {
		super(ctx);
	}

	public Publicacion transicionar(Publicacion ctx) {
		
		// si excedio el tiempo de vigencia para estar abierta
		if(Instant.now().toDateTime().isAfter(ctx.getFechaVigencia())) {
			ctx.setEstado(new EstadoPublicacionCerrado(ctx));			
			ctx.setFechaVigencia(new DateTime().plusDays(/*properties.getDias()*/14)); // TODO fix propeties coming null
			LOGGER.info("Publicacion ID(" + ctx.getId() + ") cerrada automaticamente por cumplir tiempo de vigencia abierto.");
		}
		
		return ctx;
	}

	@Override
	public void open(Publicacion ctx) {
		throw new InvalidActionException("No se pueden abrir publicaciones "
				+ "que se encuentren abiertas.");
	}

}
