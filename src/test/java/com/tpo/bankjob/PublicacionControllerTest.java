package com.tpo.bankjob;

import java.util.Optional;
import java.util.UUID;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tpo.bankjob.model.Empresa;
import com.tpo.bankjob.model.Publicacion;
import com.tpo.bankjob.model.repository.EmpresaRepository;
import com.tpo.bankjob.model.repository.PublicacionRepository;
import com.tpo.bankjob.model.vo.Modalidad;
import com.tpo.bankjob.model.vo.TipoTrabajo;
import com.tpo.bankjob.security.RequestTokenService;

@SpringBootTest
public class PublicacionControllerTest {
	
    @Autowired
    Publicacion publicacion;
    
    @Autowired
    EmpresaRepository empresaRepository;
    
    @Autowired
    PublicacionRepository publicacionRepository;
		
	@Test
	public void givenValidEmpresaVoAndPublicacionVoWhenPublicacionIsAddedThenSaveItSucessfully() {
		
		// given
		Empresa empresaVO = new Empresa(UUID.randomUUID().toString(),
				"empresa1",
				"1234");
		RequestTokenService.setRequestToken(empresaVO.getId());
		empresaRepository.saveAndFlush(empresaVO);
		
		Publicacion publicacionVO = new Publicacion(empresaVO,
				"Publicacion1", 
				"Descripcion", 
				Modalidad.FULL_TIME, 
				TipoTrabajo.PRESENCIAL, 
				"locacion",
				"Categoria",
				Double.valueOf(100),
				new DateTime());
		
		//  when
		publicacion.add(publicacionVO);
		
		// then
		Empresa resultEmpresa = null;
		Publicacion resultPublicacion = null;
		
		Optional<Empresa> optResultEmpresa = empresaRepository.findById(empresaVO.getId());
		Optional<Publicacion> optResultPublicacion = publicacionRepository.findById(publicacionVO.getId());
		
		if(optResultEmpresa.isPresent())
			resultEmpresa = optResultEmpresa.get();
		
		if(optResultPublicacion.isPresent())
			resultPublicacion = optResultPublicacion.get();
		
		Assert.assertNotNull(resultEmpresa);
		Assert.assertNotNull(resultPublicacion);
		Assert.assertTrue(empresaRepository.findById(empresaVO.getId()).get().getPublicaciones().get(0).equals(resultPublicacion));
		Assert.assertTrue(resultPublicacion.getEmpresa().getId().equals(empresaVO.getId()));
	}
	
	
	@Test
	public void givenValidPublicacionVOWithoutTitleWhenAddPublicacionThenGenerateTitleAndSaveItSucessfully() {
		
		// given
		Empresa empresaVO = new Empresa(UUID.randomUUID().toString(),
				"empresa1",
				"1234");
		RequestTokenService.setRequestToken(empresaVO.getId());
		empresaRepository.saveAndFlush(empresaVO);
		
		Publicacion publicacionVO = new Publicacion(empresaVO,
				"", 
				"Descripcion", 
				Modalidad.FULL_TIME, 
				TipoTrabajo.PRESENCIAL, 
				"locacion",
				"Categoria",
				Double.valueOf(100),
				new DateTime());
		
		//  when
		publicacion.add(publicacionVO);
		
		// then
		Publicacion resultPublicacion = null;
		Optional<Publicacion> optResultPublicacion = publicacionRepository.findById(publicacionVO.getId());
	
		if(optResultPublicacion.isPresent())
			resultPublicacion = optResultPublicacion.get();
		
		Assert.assertNotNull(resultPublicacion);
		Assert.assertTrue(!resultPublicacion.getTitulo().isBlank() 
				&& resultPublicacion.getTitulo().contains(resultPublicacion.getLocacion()));
		
	}
}
