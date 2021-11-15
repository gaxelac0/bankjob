package com.tpo.bankjob.model.dao;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tpo.bankjob.model.Empresa;
import com.tpo.bankjob.model.exception.AlreadyRegisteredUserException;
import com.tpo.bankjob.model.repository.EmpresaRepository;
import com.tpo.bankjob.security.UserCrudService;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Component
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class EmpresaDao {
	
	@Autowired
	UserCrudService users;

	@Autowired
	EmpresaRepository empresaRepository;

	public String register(Empresa empresaVO) {
		
		Empresa empresa =  empresaRepository.findByUsername(empresaVO.getUsername());
		if(empresa != null)
			throw new AlreadyRegisteredUserException(empresaVO.getUsername());
		
		empresaVO.setId(UUID.randomUUID().toString());
		empresaRepository.saveAndFlush(empresaVO);
		users.save(empresaVO.getId(), empresaVO);
		return empresaVO.getId();
	}

	public Optional<Empresa> findById(String id) {
		return empresaRepository.findById(id);
	}
	
}
