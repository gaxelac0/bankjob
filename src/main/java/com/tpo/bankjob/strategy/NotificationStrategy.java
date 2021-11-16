package com.tpo.bankjob.strategy;
import com.tpo.bankjob.model.Notificacion;

public interface NotificationStrategy {
	public void send(Notificacion notificacion);
}
