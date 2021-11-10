package com.tpo.bankjob.strategy.adapter;

import com.tpo.bankjob.model.vo.Notificacion;

public class PushAdapter implements PushAdapterNotifier {

	public void push(Notificacion notificacion) {
		// PUSH SERV
		System.out.println("Enviando push...");
	}

}
