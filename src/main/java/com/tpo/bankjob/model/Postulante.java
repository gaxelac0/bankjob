package com.tpo.bankjob.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tpo.bankjob.model.dao.PostulanteDao;
import com.tpo.bankjob.model.vo.InteresVO;
import com.tpo.bankjob.model.vo.PostulanteVO;

@Component
public class Postulante {
	
	@Autowired
	private PostulanteDao postulanteDao;
	
	public Postulante () {}
	
	public String register(PostulanteVO postulanteVO) {
		return postulanteDao.register(postulanteVO);
	}

	public List<PostulanteVO> findAll() {
		return postulanteDao.findAll();
	}

	public Optional<PostulanteVO> findById(String id) {
		return postulanteDao.findById(id);
	}

	public InteresVO addInteres(InteresVO interes) {
		return postulanteDao.addInteres(interes);
	}

}
