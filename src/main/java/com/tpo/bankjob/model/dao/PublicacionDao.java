package com.tpo.bankjob.model.dao;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tpo.bankjob.model.Empresa;
import com.tpo.bankjob.model.Publicacion;
import com.tpo.bankjob.model.builder.PublicacionBuilder;
import com.tpo.bankjob.model.builder.PublicacionVOBuilder;
import com.tpo.bankjob.model.exception.EmpresaNotFoundException;
import com.tpo.bankjob.model.exception.InvalidActionException;
import com.tpo.bankjob.model.repository.EmpresaRepository;
import com.tpo.bankjob.model.repository.PublicacionRepository;
import com.tpo.bankjob.model.repository.SkillRepository;
import com.tpo.bankjob.model.repository.TareaRepository;
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
		
		Optional<Empresa> opt = empresaRepository.findById(RequestTokenService.getRequestToken());
		if(!opt.isPresent()) {
			throw new EmpresaNotFoundException(RequestTokenService.getRequestToken());
		}
		
		Empresa empresa = opt.get();
		
		publicacionVO.setEmpresa(empresa);
						
		// se genera la publicacion a traves del builder 
		Publicacion publicacion = new PublicacionBuilder(publicacionVO).build();
		publicacionRepository.saveAndFlush(publicacion); // se guarda para generar el id
		
		// vincula los skills y tareas a la publicacion
		publicacion.getSkills().stream().forEach((rs) ->
			rs.setOwnerId(publicacion.getId().toString())
		);
		publicacion.getTareas().stream().forEach((t) ->
			t.setPublicacion(publicacion)
		);
		
		// guardamos los skills y tareas
		skillRepository.saveAllAndFlush(publicacion.getSkills());
		tareaRepository.saveAllAndFlush(publicacion.getTareas());
		
		// guardamos la publicacion con las relaciones
		publicacionRepository.saveAndFlush(publicacion);
		
		// vinculamos la publicacion generada con la empresa
		empresa.addPublicacion(publicacion);
		empresaRepository.saveAndFlush(empresa);
		
		return new PublicacionVOBuilder(publicacion).build();
	}

	public Optional<Publicacion> get(String id) {
		return publicacionRepository.findById(id);
	}

	public List<Publicacion> findAll() {
		return publicacionRepository.findAll();
	}

	public boolean open(Publicacion publicacion) {
		
		Optional<Empresa> opt = empresaRepository.findById(RequestTokenService.getRequestToken());
		if(!opt.isPresent()) {
			throw new EmpresaNotFoundException(
					RequestTokenService.getRequestToken());
		}
		
		Empresa empresaVO = opt.get();
		if(!publicacion.getEmpresa().getId().equalsIgnoreCase(empresaVO.getId())) {
			throw new InvalidActionException("Solo la propia empresa puede"
					+ " abrir sus publicaciones cerradas.");
		}
		
		publicacion.getEstado().open(publicacion);
		publicacionRepository.saveAndFlush(publicacion);
		
		LOGGER.info("Publicacion ID(" + publicacion.getId() + ") abierta manualmente por la empresa.");
		return true;
	}
		
    public void transicionarPublicaciones() {
    	
		// las publicaciones finalizadas no necesitan ser transicionadas
		publicacionRepository.findAll().stream()
		.filter((p) -> p.isClosed() || p.isOpen())
		.forEach((p) -> publicacionRepository.saveAndFlush(p.transicionar()));
    }

}
