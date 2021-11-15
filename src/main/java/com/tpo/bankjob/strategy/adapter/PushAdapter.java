package com.tpo.bankjob.strategy.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tpo.bankjob.model.vo.Notificacion;

public class PushAdapter implements PushAdapterNotifier {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PushAdapter.class);

	public void push(Notificacion notificacion) {
		// PUSH SERV
		LOGGER.info("Enviando push: " + notificacion.getMensaje());
	}

}
