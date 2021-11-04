package com.tpo.bankjob.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tpo.bankjob.model.dao.PostulacionDao;
import com.tpo.bankjob.model.vo.PostulacionVO;

@Component
public class Postulacion {
	
	@Autowired
	private PostulacionDao postulacionDao;
	
	public Postulacion () {}
	
	public PostulacionVO add(PostulacionVO postulacionVO) {
		return postulacionDao.add(postulacionVO);
	}
}