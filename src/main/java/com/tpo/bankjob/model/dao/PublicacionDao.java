package com.tpo.bankjob.model.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tpo.bankjob.model.exception.EmpresaNotFoundException;
import com.tpo.bankjob.model.repository.EmpresaRepository;
import com.tpo.bankjob.model.repository.PublicacionRepository;
import com.tpo.bankjob.model.vo.EmpresaVO;
import com.tpo.bankjob.model.vo.PublicacionVO;

@Component
public class PublicacionDao {
	
	@Autowired
	PublicacionRepository publicacionRepository;
	
	@Autowired
	EmpresaRepository empresaRepository;

	public PublicacionVO addPublicacion(PublicacionVO publicacionVO) {
		
		Optional<EmpresaVO> opt = empresaRepository.findById(publicacionVO.getIdEmpresa());
		if(!opt.isPresent()) {
			throw new EmpresaNotFoundException(publicacionVO.getIdEmpresa());
		}
		
		EmpresaVO empresaVO = opt.get();
		
		// agrega la publicacion al repo de publicaciones
		publicacionRepository.saveAndFlush(publicacionVO);

		// actualizar el obj empresa y el repo
		empresaVO.addPublicacion(publicacionVO);
		empresaRepository.saveAndFlush(empresaVO);
		
		return publicacionVO;
	}

}
