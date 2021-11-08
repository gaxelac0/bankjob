package com.tpo.bankjob.controller;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tpo.bankjob.model.Empresa;
import com.tpo.bankjob.model.exception.EmpresaNotFoundException;
import com.tpo.bankjob.model.vo.EmpresaVO;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/empresa")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class EmpresaController {
	
	@Autowired
	Empresa empresa;

	@GetMapping("/{id}")
	@ResponseBody EmpresaVO get(
			@PathVariable String id) {
		
		Optional<EmpresaVO> opt = empresa.findById(id);
		if(!opt.isPresent()) {
			throw new EmpresaNotFoundException(id);
		}
		
		return opt.get();
	}	
}
