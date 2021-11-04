package com.tpo.bankjob.model.dao;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tpo.bankjob.model.exception.AlreadyRegisteredUserException;
import com.tpo.bankjob.model.repository.PostulanteRepository;
import com.tpo.bankjob.model.repository.SkillRepository;
import com.tpo.bankjob.model.vo.PostulanteVO;
import com.tpo.bankjob.security.UserCrudService;

@Component
public class PostulanteDao {
	
	@Autowired
	UserCrudService users;
	
	@Autowired
	PostulanteRepository postulanteRepository;
	
	@Autowired
	SkillRepository skillRepository;

	public String register(PostulanteVO postulanteVO) {
		
		PostulanteVO postulante =  postulanteRepository.findByUsername(postulanteVO.getUsername());
		if(postulante != null) 
			throw new AlreadyRegisteredUserException(postulante.getUsername());
		
		postulanteVO.setId(UUID.randomUUID().toString());
		postulanteVO.getSkills().stream().forEach((s) -> s.setOwnerId(postulanteVO.getId()));
		skillRepository.saveAllAndFlush(postulanteVO.getSkills());
		postulanteRepository.saveAndFlush(postulanteVO);
		users.save(postulanteVO.getId(), postulanteVO);
		return postulanteVO.getId();
	}

}
