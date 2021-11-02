package com.tpo.bankjob.controller;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tpo.bankjob.model.exception.AlreadyRegisteredUserException;
import com.tpo.bankjob.model.repository.EmpresaRepository;
import com.tpo.bankjob.model.repository.PostulanteRepository;
import com.tpo.bankjob.model.utils.CredentialsWrapper;
import com.tpo.bankjob.model.utils.LoginResponseWrapper;
import com.tpo.bankjob.model.vo.EmpresaVO;
import com.tpo.bankjob.model.vo.PostulanteVO;
import com.tpo.bankjob.security.UserAuthenticationService;
import com.tpo.bankjob.security.UserCrudService;

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
	
	@NonNull
	UserCrudService users;

	@Autowired
	EmpresaRepository empresaRepository;
	
	@Autowired
	PostulanteRepository postulanteRepository;

	@PostMapping("/empresa/register")
	String register(@RequestBody EmpresaVO empresaVO,
			BindingResult bindingResult) throws RuntimeException {

		EmpresaVO empresa =  empresaRepository.findByUsername(empresaVO.getUsername());
		if(empresa != null) {
			throw new AlreadyRegisteredUserException(empresaVO.getUsername());
		}
		empresaVO.setId(UUID.randomUUID().toString());

		empresaRepository.saveAndFlush(empresaVO);
		
		users.save(empresaVO.getId(), empresaVO);
		
		return empresaVO.getId();
	}
	
	@PostMapping("/postulante/register")
	String register(@RequestBody PostulanteVO postulanteVO,
			BindingResult bindingResult) throws RuntimeException {

		PostulanteVO postulante =  postulanteRepository.findByUsername(postulanteVO.getUsername());
		if(postulante != null) {
			throw new AlreadyRegisteredUserException(postulanteVO.getUsername());
		}
		postulanteVO.setId(UUID.randomUUID().toString());

		postulanteRepository.saveAndFlush(postulanteVO);
		
		users.save(postulanteVO.getId(), postulanteVO);
		
		return postulanteVO.getId();
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