package com.tpo.bankjob.strategy;
import com.tpo.bankjob.model.vo.Notificacion;

public interface NotificationStrategy {
	public void send(Notificacion notificacion);
}
