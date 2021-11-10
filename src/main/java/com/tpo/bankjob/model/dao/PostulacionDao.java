package com.tpo.bankjob.model.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tpo.bankjob.batch.ThreadPoolService;
import com.tpo.bankjob.model.exception.AlreadyExistsPostulacionException;
import com.tpo.bankjob.model.exception.InsufficientSkillsForPostulacionException;
import com.tpo.bankjob.model.exception.InvalidPostulacionException;
import com.tpo.bankjob.model.exception.PostulanteNotFoundException;
import com.tpo.bankjob.model.exception.PublicacionIsNotOpenException;
import com.tpo.bankjob.model.exception.PublicacionNotFoundException;
import com.tpo.bankjob.model.repository.PostulacionRepository;
import com.tpo.bankjob.model.repository.PostulanteRepository;
import com.tpo.bankjob.model.repository.PublicacionRepository;
import com.tpo.bankjob.model.repository.SkillRepository;
import com.tpo.bankjob.model.utils.PostulacionKeyWrapper;
import com.tpo.bankjob.model.utils.PostulacionUtils;
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
	
	@Autowired
	SkillRepository skillRepository;
	
	public PostulacionVO add(PostulacionVO postulacionVO) throws RuntimeException {
		
		basicValidationAndSetting(postulacionVO);
		postulacionRepository.saveAndFlush(postulacionVO);
		
		return postulacionVO;
	}
	
	public List<PostulacionVO> findAll() {
		return postulacionRepository.findAll();
	}
	

	private void basicValidationAndSetting(PostulacionVO 
			postulacionVO) throws RuntimeException {
		
		if(!RequestTokenService.getRequestToken()
				.equalsIgnoreCase(postulacionVO.getId().getIdPostulante())) {
			throw new InvalidPostulacionException("Solo el propio postulante "
					+ "se puede postular a una publicacion.");
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
        .map(obj -> {        	
            postulacionVO.setPublicacion(obj);
            return postulacionVO;
        })
        .orElseThrow(() -> new PublicacionNotFoundException(postulacionVO.getId().getIdPublicacion()));
		
		if(!postulacionVO.getPublicacion().isOpen())
			throw new PublicacionIsNotOpenException();
		
		if(!PostulacionUtils.matchHabilidades(
				postulacionVO.getPublicacion().getSkills(),
				postulacionVO.getPostulante().getSkills())) {
			throw new InsufficientSkillsForPostulacionException();
		}
		
		// (#ADOO) PATTERN SINGLETON
		ThreadPoolService.getInstance().execute(new Runnable() {
		    @Override
		    public void run() {
		    	postulacionVO.getPublicacion().notificarPostulacion();
		    }
		});
	}

	public Optional<PostulacionVO> findById(PostulacionKeyWrapper id) {
		return postulacionRepository.findById(id);
	}
	
}