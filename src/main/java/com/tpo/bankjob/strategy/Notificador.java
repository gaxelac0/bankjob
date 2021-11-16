package com.tpo.bankjob.strategy;

import com.tpo.bankjob.model.Notificacion;

public class Notificador {
	private NotificationStrategy strategy;
	
	public Notificador(NotificationStrategy strategy) {
		this.strategy = strategy;
	}
	
	public void send(Notificacion notificacion) {
		this.strategy.send(notificacion);
	}
}
