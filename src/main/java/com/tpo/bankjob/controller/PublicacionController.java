package com.tpo.bankjob.controller;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.tpo.bankjob.model.Publicacion;
import com.tpo.bankjob.model.exception.PublicacionNotFoundException;
import com.tpo.bankjob.model.utils.View;
import com.tpo.bankjob.model.vo.PublicacionVO;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/publicacion")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class PublicacionController {
	
	@Autowired
	Publicacion publicacion;
	
	@Scheduled(fixedRate = 60000)
	public void transicionarPublicaciones() {
		publicacion.transicionarPublicaciones();
	}
	
	@JsonView(View.Public.class)
	@PostMapping("/add")
	@ResponseBody PublicacionVO add(
			@RequestBody PublicacionVO publicacionVO,
			BindingResult bindingResult) {
		return publicacion.add(publicacionVO);
	}
	
	@JsonView(View.Public.class)
	@PostMapping("/open/{id}")
	@ResponseBody PublicacionVO open(
			@PathVariable String id) {
				
		return publicacion.open(getPublicacionById(id));
	}
	
	@JsonView(View.Public.class)
	@GetMapping("/all")
	@ResponseBody List<PublicacionVO> getPublicaciones() {
		return publicacion.findAll();
	}
	
	@JsonView(View.Public.class)
	@GetMapping("/all/{categoria}")
	@ResponseBody List<PublicacionVO> getPublicacionesByCategoria(
			@PathVariable String categoria) {
		return publicacion.findAll().stream()
				.filter((p) -> p.getCategoria().equalsIgnoreCase(categoria))
				.collect(Collectors.toList());
	}
	
	@JsonView(View.Internal.class)
	@GetMapping("/{id}")
	@ResponseBody PublicacionVO getPublicacionById(
			@PathVariable String id) {
		return publicacion.get(id).map((p) -> p).orElseThrow(() -> new PublicacionNotFoundException(id));
	}
	
	@JsonView(View.ExtendedPublic.class)
	@GetMapping("/empresa/{idEmpresa}")
	@ResponseBody List<PublicacionVO> getPublicacionesByIdEmpresa(
			@PathVariable String idEmpresa) {
		return publicacion.findAll().stream()
				.filter((p) -> p.getEmpresa().getId().equalsIgnoreCase(idEmpresa))
				.collect(Collectors.toList());
	}
	
	
}
