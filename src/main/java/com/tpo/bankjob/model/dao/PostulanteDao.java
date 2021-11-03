package com.tpo.bankjob.model.dao;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tpo.bankjob.model.exception.AlreadyRegisteredUserException;
import com.tpo.bankjob.model.repository.PostulanteRepository;
import com.tpo.bankjob.model.vo.PostulanteVO;
import com.tpo.bankjob.security.UserCrudService;

@Component
public class PostulanteDao {
	
	@Autowired
	UserCrudService users;
	
	@Autowired
	PostulanteRepository postulanteRepository;

	public String register(PostulanteVO postulanteVO) {
		
		PostulanteVO postulante =  postulanteRepository.findByUsername(postulanteVO.getUsername());
		if(postulante != null) 
			throw new AlreadyRegisteredUserException(postulanteVO.getUsername());
		
		postulanteVO.setId(UUID.randomUUID().toString());
		postulanteRepository.saveAndFlush(postulanteVO);
		users.save(postulanteVO.getId(), postulanteVO);
		return postulanteVO.getId();
	}

}
