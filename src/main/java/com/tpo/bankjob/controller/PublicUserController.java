package com.tpo.bankjob.controller;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tpo.bankjob.model.Empresa;
import com.tpo.bankjob.model.Postulante;
import com.tpo.bankjob.model.utils.CredentialsWrapper;
import com.tpo.bankjob.model.utils.LoginResponseWrapper;
import com.tpo.bankjob.model.vo.EmpresaVO;
import com.tpo.bankjob.model.vo.PostulanteVO;
import com.tpo.bankjob.security.UserAuthenticationService;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/public/users")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class PublicUsersController {
	
	@Autowired
	Empresa empresa;
	
	@Autowired
	Postulante postulante;
	
	@NonNull
	UserAuthenticationService authentication;

	@PostMapping("/empresa/register")
	String register(@RequestBody EmpresaVO empresaVO,
			BindingResult bindingResult) throws RuntimeException {
		return empresa.register(empresaVO);
	}
	
	@PostMapping("/postulante/register")
	String register(@RequestBody PostulanteVO postulanteVO,
			BindingResult bindingResult) throws RuntimeException {
		return postulante.register(postulanteVO);
	}

	@PostMapping("/login")
	@ResponseBody LoginResponseWrapper login(@RequestBody CredentialsWrapper credentials) {
		
		UserDetails user = authentication.login(
				credentials.username, credentials.password);
		
		String uuid;
		boolean isEmpresa = false;
		if(user instanceof EmpresaVO) {
			uuid = ((EmpresaVO)user).getId();
			isEmpresa = true;
		}
		else {
			uuid = ((PostulanteVO)user).getId();
		}
		
		return new LoginResponseWrapper(uuid, isEmpresa);
	}
}