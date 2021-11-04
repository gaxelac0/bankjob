package com.tpo.bankjob.model;

import java.util.List;
import java.util.Optional;

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
		
		// TODO if publicacion.getTitulo() == null, generar uno
		// titulo = generarTitulo();
		// publicacion.setTitulo(titulo)
		
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
}
