package com.tpo.bankjob.controller;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.tpo.bankjob.model.Interes;
import com.tpo.bankjob.model.Postulante;
import com.tpo.bankjob.model.exception.PostulanteNotFoundException;
import com.tpo.bankjob.model.utils.View;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/postulante")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class PostulanteController {
	
	@Autowired
	Postulante postulante;

	@JsonView(View.Public.class)
	@GetMapping("/{id}")
	@ResponseBody Postulante get(
			@PathVariable String id) {
		
		Optional<Postulante> opt = postulante.findById(id);
		if(!opt.isPresent()) {
			throw new PostulanteNotFoundException(id);
		}
		
		return opt.get();
	}
	
	@JsonView(View.Public.class)
	@PostMapping("/register")
	String register(@RequestBody Postulante postulanteVO,
			BindingResult bindingResult) throws RuntimeException {
		return postulante.register(postulanteVO);
	}
	
	@JsonView(View.Public.class)
	@PostMapping("interes/add")
	@ResponseBody Interes addInteres(
			@RequestBody Interes interes,
			BindingResult bindingResult) {
		return postulante.addInteres(interes);
	}

	public List<Postulante> getPostulantes() {
		return postulante.findAll();
	}
}
