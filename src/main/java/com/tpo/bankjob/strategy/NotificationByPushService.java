package com.tpo.bankjob.strategy;

import com.tpo.bankjob.model.vo.Notificacion;
import com.tpo.bankjob.strategy.adapter.PushAdapterNotifier;

public class NotificationByPushService implements NotificationStrategy {

	private PushAdapterNotifier adapter;
	
	public NotificationByPushService(PushAdapterNotifier adapter) {
		this.adapter = adapter;
	}

	public void send(Notificacion notificacion) {
		this.adapter.push(notificacion);
	}

}
