package com.tpo.bankjob.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tpo.bankjob.model.dao.PostulacionDao;
import com.tpo.bankjob.model.utils.PostulacionKeyWrapper;
import com.tpo.bankjob.model.vo.PostulacionVO;

@Component
public class Postulacion {
	
	@Autowired
	private PostulacionDao postulacionDao;
	
	public Postulacion () {}
	
	public PostulacionVO add(PostulacionVO postulacionVO) throws RuntimeException {
		return postulacionDao.add(postulacionVO);
	}

	public List<PostulacionVO> findAll() {
		return postulacionDao.findAll();
	}

	public Optional<PostulacionVO> findById(PostulacionKeyWrapper id) {
		return postulacionDao.findById(id);
	}
}
