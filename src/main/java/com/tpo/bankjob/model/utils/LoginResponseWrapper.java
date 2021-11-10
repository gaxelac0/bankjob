package com.tpo.bankjob.model.utils;

import com.fasterxml.jackson.annotation.JsonView;

@JsonView(View.Public.class)
public class LoginResponseWrapper {
	
	@JsonView(View.Public.class)
	public String uuid;
	
	@JsonView(View.Public.class)
	public boolean isEmpresa;
	
	public LoginResponseWrapper(String uuid, boolean isEmpresa) {
		this.uuid = uuid;
		this.isEmpresa = isEmpresa;
	}
}
