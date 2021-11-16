package com.tpo.bankjob.strategy.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tpo.bankjob.model.Notificacion;

public class SlackAPIAdapter implements SlackAPIAdapterNotifier {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SlackAPIAdapter.class);
	
	public void enviarSlack(Notificacion notificacion) {
		// SLACK API
		LOGGER.info("Se envio slack: " + notificacion.getMensaje());
	}

}
