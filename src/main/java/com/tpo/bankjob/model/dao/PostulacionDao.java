package com.tpo.bankjob.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tpo.bankjob.model.exception.AlreadyExistsPostulacionException;
import com.tpo.bankjob.model.exception.InvalidPostulacionException;
import com.tpo.bankjob.model.exception.PostulanteNotFoundException;
import com.tpo.bankjob.model.exception.PublicacionNotFoundException;
import com.tpo.bankjob.model.repository.PostulacionRepository;
import com.tpo.bankjob.model.repository.PostulanteRepository;
import com.tpo.bankjob.model.repository.PublicacionRepository;
import com.tpo.bankjob.model.vo.PostulacionVO;
import com.tpo.bankjob.security.RequestTokenService;
import com.tpo.bankjob.security.UserCrudService;

@Component
public class PostulacionDao {
	
	@Autowired
	UserCrudService users;
	
	@Autowired
	PublicacionRepository publicacionRepository;
	
	@Autowired
	PostulanteRepository postulanteRepository;
	
	@Autowired
	PostulacionRepository postulacionRepository;

	public PostulacionVO add(PostulacionVO postulacionVO) {
		
		basicValidationAndSetting(postulacionVO);
		postulacionRepository.saveAndFlush(postulacionVO);
		
		return postulacionVO;
	}

	private void basicValidationAndSetting(PostulacionVO postulacionVO) {
		
		if(!RequestTokenService.getRequestToken()
				.equalsIgnoreCase(postulacionVO.getId().getIdPostulante())) {
			throw new InvalidPostulacionException("Solo el propio postulante "
					+ "se puede postular a una publicación.");
		}
		
		// ya existe la publicacion
		if(postulacionRepository.findById(postulacionVO.getId()).isPresent()) 
			throw new AlreadyExistsPostulacionException(postulacionVO.getId());
		
		// el postulante debe existir
		postulanteRepository.findById(postulacionVO.getId().getIdPostulante())
        .map(obj -> {
            postulacionVO.setPostulante(obj);
            return postulacionVO;
        })
        .orElseThrow(() -> new PostulanteNotFoundException(postulacionVO.getId().getIdPostulante()));
		
		// la publicacion debe existir
		publicacionRepository.findById(postulacionVO.getId().getIdPublicacion())
		// la publicacion debe estar abierta
		.filter((p) -> p.isOpen())
		// TODO los postulantes deben cumplir con las habilidades exigidas por la publicacion
		//.filter((p) -> PostulacionUtils.matchHabilidades(p.getHabilidades(), postulacionVO.getPostulante().getHabilidades())) 
        .map(obj -> {        	
            postulacionVO.setPublicacion(obj);
            return postulacionVO;
        })
        .orElseThrow(() -> new PublicacionNotFoundException(postulacionVO.getId().getIdPublicacion()));
	}

}