package com.tpo.bankjob.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tpo.bankjob.model.Publicacion;
import com.tpo.bankjob.model.repository.EmpresaRepository;
import com.tpo.bankjob.model.repository.PublicacionRepository;
import com.tpo.bankjob.model.vo.PublicacionVO;

@Controller
@Scope("singleton")
@RequestMapping(value="/publicacion")
public class PublicacionController {
	
	@Autowired
	private Publicacion publicacion;
	
	private PublicacionController () {}
	
	@Autowired
	EmpresaRepository empresaRepository;
	
	@Autowired
	PublicacionRepository publicacionRepository;
		
	/**
	 * method that adds a new publication for an existing empresa
	 * @param empresaVO
	 * @param publicacionVO
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody PublicacionVO addPublicacion(@RequestBody PublicacionVO publicacionVo,
			BindingResult bindingResult) {
		return publicacion.addPublicacion(publicacionVo);
	}
}
