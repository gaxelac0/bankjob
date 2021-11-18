package com.tpo.bankjob.controller;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.tpo.bankjob.model.Empresa;
import com.tpo.bankjob.model.Postulante;
import com.tpo.bankjob.model.utils.CredentialsWrapper;
import com.tpo.bankjob.model.utils.LoginResponseWrapper;
import com.tpo.bankjob.model.utils.View;
import com.tpo.bankjob.security.UserAuthenticationService;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/public/users")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class PublicUsersController {
		
	@NonNull
	UserAuthenticationService authentication;

	@JsonView(View.Public.class)
	@PostMapping("/login")
	@ResponseBody LoginResponseWrapper login(
			@RequestBody CredentialsWrapper credentials) {
		
		UserDetails user = authentication.login(
				credentials.username, credentials.password);
		
		String uuid;
		boolean isEmpresa = false;
		if(user instanceof Empresa) {
			uuid = ((Empresa)user).getId();
			isEmpresa = true;
		}
		else {
			uuid = ((Postulante)user).getId();
		}
		
		return new LoginResponseWrapper(uuid, isEmpresa);
	}
}