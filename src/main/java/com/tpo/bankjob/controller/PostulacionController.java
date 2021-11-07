package com.tpo.bankjob.controller;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tpo.bankjob.model.Postulacion;
import com.tpo.bankjob.model.vo.PostulacionVO;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/postulacion")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class PostulacionController {
	
	@NonNull
	@Autowired
	Postulacion postulacion;
		
	@PostMapping("/add")
	@ResponseBody PostulacionVO add(
			@RequestBody PostulacionVO postulacionVO,
			BindingResult bindingResult) {
		return postulacion.add(postulacionVO);
	}
}
