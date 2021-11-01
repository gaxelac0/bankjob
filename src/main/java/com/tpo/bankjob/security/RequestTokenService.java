package com.tpo.bankjob.security;

public class RequestTokenService {
	
	private static String token;
	public static String getRequestToken() {
		return RequestTokenService.token;
	}
	
	public static void setRequestToken(String token) {
		RequestTokenService.token = token;
	}
}
