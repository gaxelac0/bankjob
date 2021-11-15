package com.tpo.bankjob.model.state;

import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tpo.bankjob.model.Publicacion;
import com.tpo.bankjob.model.exception.InvalidActionException;

public class EstadoPublicacionCerrado extends EstadoPublicacion {
	
	//@Autowired
	//@JsonIgnore
   // public GlobalProperties properties;

	private static final long serialVersionUID = 4067463337202370244L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EstadoPublicacionCerrado.class);
		
	public EstadoPublicacionCerrado(Publicacion ctx) {
		super(ctx);
	}

	public Publicacion transicionar(Publicacion ctx) {
		
		// si excedio el tiempo de vigencia para estar cerrada (y poder volverse a abrir)
		if(Instant.now().isAfter(ctx.getFechaVigencia())) {
			
			// pasa a un estado final irreversible
			ctx.setEstado(new EstadoPublicacionFinal(ctx));
			LOGGER.info("Publicacion ID(" + ctx.getId() + ") finalizada automaticamente cumplir tiempo de vigencia cerrado.");
		}
		
		return ctx;
	}

	@Override
	public void open(Publicacion ctx) {
		ctx.setFechaVigencia(Instant.now().toDateTime().plusWeeks(2)); ///* TODO properties.getDias()*/
		ctx.setEstado(new EstadoPublicacionAbierto(ctx));
	}
	
	

}
