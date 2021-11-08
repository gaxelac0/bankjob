package com.tpo.bankjob.model;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tpo.bankjob.model.dao.EmpresaDao;
import com.tpo.bankjob.model.vo.EmpresaVO;

@Component
public class Empresa {
	
	@Autowired
	private EmpresaDao empresaDao;

	public Empresa() {}
	
	public String register(EmpresaVO empresaVO) {
		return empresaDao.register(empresaVO);
	}

	public Optional<EmpresaVO> findById(String id) {
		return empresaDao.findById(id);
	}

}
