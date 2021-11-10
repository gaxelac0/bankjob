package com.tpo.bankjob.strategy.adapter;

import com.tpo.bankjob.model.vo.Notificacion;

public class SlackAPIAdapter implements SlackAPIAdapterNotifier {
	public void enviarSlack(Notificacion notificacion) {
		// SLACK API
		System.out.println("Se envio slack.");
	}

}
