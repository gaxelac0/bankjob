package com.tpo.bankjob.strategy;

import com.tpo.bankjob.model.Notificacion;
import com.tpo.bankjob.strategy.adapter.SlackAPIAdapterNotifier;

public class NotificationBySlackAPI implements NotificationStrategy {

	private SlackAPIAdapterNotifier adapter;
	
	public NotificationBySlackAPI(SlackAPIAdapterNotifier adapter) {
		this.adapter = adapter;
	}

	public void send(Notificacion notificacion) {
		this.adapter.enviarSlack(notificacion);
	}
}
