package com.tpo.bankjob.controller;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.tpo.bankjob.model.Empresa;
import com.tpo.bankjob.model.exception.EmpresaNotFoundException;
import com.tpo.bankjob.model.utils.View;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Controller
@RequestMapping("/empresa")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class EmpresaController {
	
	@Autowired
	Empresa empresa;

	@JsonView(View.ExtendedPublic.class)
	@GetMapping("/{id}")
	@ResponseBody Empresa get(
			@PathVariable String id) {
		
		Optional<Empresa> opt = empresa.get(id);
		if(!opt.isPresent()) {
			throw new EmpresaNotFoundException(id);
		}
		
		return opt.get();
	}
	
	@JsonView(View.Public.class)
	@PostMapping("/register")
	String register(@RequestBody Empresa empresaVO,
			BindingResult bindingResult) throws RuntimeException {
		return empresa.register(empresaVO);
	}
}
