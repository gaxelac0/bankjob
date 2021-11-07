package com.tpo.bankjob.batch;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolService {
	
	private static ExecutorService instance;
	
	private ThreadPoolService() {}

	public static ExecutorService getInstance() {
		
		if(Objects.isNull(instance)) {
			instance = Executors.newCachedThreadPool();
		}
		
		return instance;
	}	

}
