package com.tpo.bankjob.strategy;

import com.tpo.bankjob.model.vo.Notificacion;
import com.tpo.bankjob.strategy.adapter.AdapterNotificadorEmail;

public class NotificationByMail implements NotificationStrategy {

	private AdapterNotificadorEmail adapter;
	
	public NotificationByMail(AdapterNotificadorEmail adapter) {
		this.adapter = adapter;
	}

	public void send(Notificacion notificacion) {
		this.adapter.enviarEmail(notificacion);
	}

}
