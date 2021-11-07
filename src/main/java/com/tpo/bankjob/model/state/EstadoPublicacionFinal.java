package com.tpo.bankjob.model.state;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tpo.bankjob.model.vo.PublicacionVO;

public class EstadoPublicacionFinal extends EstadoPublicacion {

	private static final long serialVersionUID = 2173528406437960326L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EstadoPublicacionFinal.class);

	public EstadoPublicacionFinal(PublicacionVO ctx) {
		super(ctx);
	}

	public PublicacionVO transicionar(PublicacionVO ctx) {
		LOGGER.info("No se llegara a esta instancia nunca. "
				+ "Irreversible. "
				+ "La publicacion ya se encuentra finalizada.");
		return ctx;
	}

}
