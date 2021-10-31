package com.tpo.bankjob.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tpo.bankjob.model.dao.PublicacionDao;
import com.tpo.bankjob.model.vo.PublicacionVO;

@Component
public class Publicacion {
	
	@Autowired
	private PublicacionDao publicacionDao;

	public Publicacion () {}
	
	public PublicacionVO addPublicacion(PublicacionVO publicacion) {
		return publicacionDao.addPublicacion(publicacion);
	}
}
