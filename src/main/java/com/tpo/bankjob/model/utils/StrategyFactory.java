package com.tpo.bankjob.model.utils;

import com.tpo.bankjob.model.CanalNotificacion;
import com.tpo.bankjob.strategy.NotificationByMail;
import com.tpo.bankjob.strategy.NotificationByPushService;
import com.tpo.bankjob.strategy.NotificationBySlackAPI;
import com.tpo.bankjob.strategy.NotificationStrategy;
import com.tpo.bankjob.strategy.adapter.AdapterEmailJavaEmail;
import com.tpo.bankjob.strategy.adapter.PushAdapter;
import com.tpo.bankjob.strategy.adapter.SlackAPIAdapter;

public class StrategyFactory {
	
	public static NotificationStrategy getStrategy(CanalNotificacion canal) { 

		NotificationStrategy strategy = null;
		switch(canal) {
			case MAIL: strategy = new NotificationByMail(new AdapterEmailJavaEmail()); break;
			case PUSH: strategy = new NotificationByPushService(new PushAdapter()); break;
			case SLACK: strategy = new NotificationBySlackAPI(new SlackAPIAdapter()); break;
		}
		
		return strategy;
	}

}
