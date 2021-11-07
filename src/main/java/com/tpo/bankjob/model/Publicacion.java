package com.tpo.bankjob.model;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tpo.bankjob.model.dao.PublicacionDao;
import com.tpo.bankjob.model.vo.PublicacionVO;

@Component
public class Publicacion {
	
	@Autowired
	private PublicacionDao publicacionDao;

	public Publicacion () {}
	
	public PublicacionVO add(PublicacionVO publicacionVO) {
		
		if(StringUtils.isBlank(publicacionVO.getTitulo())) {
			publicacionVO.setTitulo(publicacionVO.getLugar() + " | "
					+ publicacionVO.getCategoria() + " | "
					+ publicacionVO.getTipoTrabajo()  + " | "
					+ (!publicacionVO.getSkills().isEmpty() 
							? publicacionVO.getSkills().get(0).getName().concat(" ") 
									: "Trabajo ")
					+ publicacionVO.getSueldoOfrecido() + "$");
		}
		
		// TODO generar imagen representativa y guardar en el disco
		// publicacionVO deberia tener un URL a la imagen generada
		// imageUri = generarImagen(); // BASE 64
		// publicacion.setImageURI(imageUri)
		
		return publicacionDao.add(publicacionVO);
	}

	public Optional<PublicacionVO> get(String id) {
		return publicacionDao.get(id);
	}

	public List<PublicacionVO> findAll() {
		return publicacionDao.findAll();
	}

	public PublicacionVO open(PublicacionVO publicacionVO) {
		return publicacionDao.open(publicacionVO);
	}
	
	public void transicionarPublicaciones() {
		publicacionDao.transicionarPublicaciones();
	}
}
