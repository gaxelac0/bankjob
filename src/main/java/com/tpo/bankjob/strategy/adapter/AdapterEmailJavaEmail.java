package com.tpo.bankjob.strategy.adapter;

import com.tpo.bankjob.model.vo.Notificacion;

public class AdapterEmailJavaEmail implements AdapterNotificadorEmail {
	
	public void enviarEmail(Notificacion notificacion) {
		// MAIL API
		System.out.println("Se envio notificacion por mail.");
	}

}
