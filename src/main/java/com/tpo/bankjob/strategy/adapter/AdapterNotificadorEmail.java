package com.tpo.bankjob.strategy.adapter;

import com.tpo.bankjob.model.vo.Notificacion;

public interface AdapterNotificadorEmail {
	void enviarEmail(Notificacion notificacion);

}
