package com.tpo.bankjob.strategy.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tpo.bankjob.model.Notificacion;

public class AdapterEmailJavaEmail implements AdapterNotificadorEmail {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdapterEmailJavaEmail.class);
	
	public void enviarEmail(Notificacion notificacion) {
		// MAIL API
		LOGGER.info("Se envio notificacion por mail: " + notificacion.getMensaje());
	}

}
