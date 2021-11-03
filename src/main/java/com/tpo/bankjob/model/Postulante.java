package com.tpo.bankjob.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tpo.bankjob.model.dao.PostulanteDao;
import com.tpo.bankjob.model.vo.PostulanteVO;

@Component
public class Postulante {
	
	@Autowired
	private PostulanteDao postulanteDao;
	
	public Postulante () {}
	
	public String register(PostulanteVO postulanteVO) {
		return postulanteDao.register(postulanteVO);
	}

}
