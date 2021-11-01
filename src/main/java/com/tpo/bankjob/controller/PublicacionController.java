package com.tpo.bankjob.controller;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tpo.bankjob.model.Publicacion;
import com.tpo.bankjob.model.vo.PublicacionVO;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/publicacion")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class PublicacionController {
	
	@NonNull
	@Autowired
	Publicacion publicacion;
		
	//@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
	@PostMapping("/add")
	@ResponseBody PublicacionVO addPublicacion(
			@RequestBody PublicacionVO publicacionVo,
			BindingResult bindingResult) {
		return publicacion.addPublicacion(publicacionVo);
	}
}
