package com.tpo.bankjob.model.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tpo.bankjob.model.exception.EmpresaNotFoundException;
import com.tpo.bankjob.model.repository.EmpresaRepository;
import com.tpo.bankjob.model.repository.PublicacionRepository;
import com.tpo.bankjob.model.vo.EmpresaVO;
import com.tpo.bankjob.model.vo.PublicacionVO;
import com.tpo.bankjob.security.RequestTokenService;

@Component
public class PublicacionDao {
	
	@Autowired
	PublicacionRepository publicacionRepository;
	
	@Autowired
	EmpresaRepository empresaRepository;
	
	public PublicacionVO add(PublicacionVO publicacionVO) {
		
		Optional<EmpresaVO> opt = empresaRepository.findById(RequestTokenService.getRequestToken());
		if(!opt.isPresent()) {
			throw new EmpresaNotFoundException(RequestTokenService.getRequestToken());
		}
		
		EmpresaVO empresaVO = opt.get();
		
		// agrega la publicacion al repo de publicaciones
		publicacionVO.setIdEmpresa(empresaVO.getId());
		publicacionRepository.saveAndFlush(publicacionVO);

		// actualizar el obj empresa y el repo
		empresaVO.addPublicacion(publicacionVO);
		empresaRepository.saveAndFlush(empresaVO);
		
		return publicacionVO;
	}

	public Optional<PublicacionVO> get(Long id) {
		return publicacionRepository.findById(id);
	}

	public List<PublicacionVO> findAll() {
		return publicacionRepository.findAll();
	}

}
