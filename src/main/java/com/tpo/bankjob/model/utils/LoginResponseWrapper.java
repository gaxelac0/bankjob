package com.tpo.bankjob.model.utils;

public class LoginResponseWrapper {
	
	public String uuid;
	public boolean isEmpresa;
	
	public LoginResponseWrapper(String uuid, boolean isEmpresa) {
		this.uuid = uuid;
		this.isEmpresa = isEmpresa;
	}
}
