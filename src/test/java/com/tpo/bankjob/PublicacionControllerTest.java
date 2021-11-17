package com.tpo.bankjob;

import java.util.Optional;
import java.util.UUID;

import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.common.collect.Lists;
import com.tpo.bankjob.model.Empresa;
import com.tpo.bankjob.model.Modalidad;
import com.tpo.bankjob.model.Publicacion;
import com.tpo.bankjob.model.Skill;
import com.tpo.bankjob.model.TipoTrabajo;
import com.tpo.bankjob.model.builder.PublicacionVOBuilder;
import com.tpo.bankjob.model.repository.EmpresaRepository;
import com.tpo.bankjob.model.repository.PublicacionRepository;
import com.tpo.bankjob.model.vo.PublicacionVO;
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
		
		// (#ADOO) aplicando builder pattern
		PublicacionVO vo = new PublicacionVOBuilder()
				.setTitulo("NODE JS 100% remoto")
				.setDescripcion("Breve descripcion")
				.setCategoria("Petrolera")
				.setModalidad(Modalidad.FULL_TIME)
				.setTipoTrabajo(TipoTrabajo.REMOTO)
				.setLocacion("Buenos Aires")
				.setSueldoOfrecido(Double.valueOf(100))
				.setFechaVigencia(Instant.now().toDateTime())
				.build();
		
		//  when
		publicacion.add(vo);
		
		// then
		Empresa resultEmpresa = null;
		Publicacion resultPublicacion = null;
		
		Optional<Empresa> optResultEmpresa = empresaRepository.findById(empresaVO.getId());
		Optional<Publicacion> optResultPublicacion = publicacionRepository.findById(vo.getId());
		
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
		
		PublicacionVO publicacionVO = new PublicacionVOBuilder()
				//.setTitulo("NODE JS 100% remoto") sin titulo
				.setDescripcion("Breve descripcion")
				.setCategoria("Petrolera")
				.setModalidad(Modalidad.FULL_TIME)
				.setTipoTrabajo(TipoTrabajo.REMOTO)
				.setLocacion("Buenos Aires")
				.setSueldoOfrecido(Double.valueOf(100))
				.setFechaVigencia(Instant.now().toDateTime())
				.build();
		
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
