package com.tpo.bankjob.controller;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

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

import com.tpo.bankjob.model.Postulante;
import com.tpo.bankjob.model.exception.PostulanteNotFoundException;
import com.tpo.bankjob.model.vo.InteresVO;
import com.tpo.bankjob.model.vo.PostulanteVO;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/postulante")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class PostulanteController {
	
	@Autowired
	Postulante postulante;

	@GetMapping("/{id}")
	@ResponseBody PostulanteVO get(
			@PathVariable String id) {
		
		Optional<PostulanteVO> opt = postulante.findById(id);
		if(!opt.isPresent()) {
			throw new PostulanteNotFoundException(id);
		}
		
		return opt.get();
	}
	
	@PostMapping("interes/add")
	@ResponseBody InteresVO addInteres(
			@RequestBody InteresVO interes,
			BindingResult bindingResult) {
		return postulante.addInteres(interes);
	}	
}
