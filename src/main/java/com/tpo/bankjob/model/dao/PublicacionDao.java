package com.tpo.bankjob.model.dao;

import java.util.List;
import java.util.Optional;

import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tpo.bankjob.model.exception.EmpresaNotFoundException;
import com.tpo.bankjob.model.exception.InvalidActionException;
import com.tpo.bankjob.model.repository.EmpresaRepository;
import com.tpo.bankjob.model.repository.PublicacionRepository;
import com.tpo.bankjob.model.repository.SkillRepository;
import com.tpo.bankjob.model.repository.TareaRepository;
import com.tpo.bankjob.model.state.EstadoPublicacionAbierto;
import com.tpo.bankjob.model.vo.EmpresaVO;
import com.tpo.bankjob.model.vo.PublicacionVO;
import com.tpo.bankjob.security.RequestTokenService;

@Component
public class PublicacionDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PublicacionDao.class);
	
	@Autowired
	PublicacionRepository publicacionRepository;
	
	@Autowired
	EmpresaRepository empresaRepository;
	
	@Autowired
	SkillRepository skillRepository;
	
	@Autowired
	TareaRepository tareaRepository;
	
	public PublicacionVO add(PublicacionVO publicacionVO) {
		
		Optional<EmpresaVO> opt = empresaRepository.findById(RequestTokenService.getRequestToken());
		if(!opt.isPresent()) {
			throw new EmpresaNotFoundException(RequestTokenService.getRequestToken());
		}
		
		EmpresaVO empresaVO = opt.get();
		
		// agrega la publicacion al repo de publicaciones
		publicacionVO.setEmpresa(empresaVO);
		publicacionRepository.save(publicacionVO);
		publicacionVO.getSkills().stream().forEach((rs) ->
			rs.setOwnerId(publicacionVO.getId().toString())
		);
		
		publicacionVO.getTareas().stream().forEach((t) ->
			t.setPublicacion(publicacionVO)
		);
		
		skillRepository.saveAllAndFlush(publicacionVO.getSkills());
		tareaRepository.saveAllAndFlush(publicacionVO.getTareas());
		publicacionRepository.saveAndFlush(publicacionVO);
		
		// actualizar el obj empresa y el repo
		empresaVO.addPublicacion(publicacionVO);
		empresaRepository.saveAndFlush(empresaVO);
		
		return publicacionVO;
	}

	public Optional<PublicacionVO> get(String id) {
		return publicacionRepository.findById(id);
	}

	public List<PublicacionVO> findAll() {
		return publicacionRepository.findAll();
	}

	public PublicacionVO open(PublicacionVO publicacionVO) {
		
		Optional<EmpresaVO> opt = empresaRepository.findById(RequestTokenService.getRequestToken());
		if(!opt.isPresent()) {
			throw new EmpresaNotFoundException(
					RequestTokenService.getRequestToken());
		}
		
		EmpresaVO empresaVO = opt.get();
		if(!publicacionVO.getEmpresa().getId().equalsIgnoreCase(empresaVO.getId())) {
			throw new InvalidActionException("Solo la propia empresa puede"
					+ " abrir sus publicaciones cerradas.");
		}
		
		if(!publicacionVO.isClosed()) {
			throw new InvalidActionException("No se pueden abrir publicaciones "
					+ "que no se encuentren en estado cerrado.");
		}
		
		publicacionVO.setFechaVigencia(Instant.now().toDateTime().plusWeeks(2));
		publicacionVO.setEstado(new EstadoPublicacionAbierto(publicacionVO));
		publicacionRepository.saveAndFlush(publicacionVO);
		
		LOGGER.info("Publicacion ID(" + publicacionVO.getId() + ") abierta manualmente por la empresa.");
		return publicacionVO;
	}
	
    public void transicionarPublicaciones() {
    	
		// las publicaciones finalizadas no necesitan ser transicionadas
		publicacionRepository.findAll().stream()
		.filter((p) -> p.isClosed() || p.isOpen())
		.forEach((p) -> publicacionRepository.saveAndFlush(p.transicionar()));
    }

}
