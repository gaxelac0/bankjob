package com.tpo.bankjob;

import java.util.Objects;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.common.collect.Lists;
import com.tpo.bankjob.controller.ReporteController;
import com.tpo.bankjob.model.Empresa;
import com.tpo.bankjob.model.Modalidad;
import com.tpo.bankjob.model.Postulacion;
import com.tpo.bankjob.model.Postulante;
import com.tpo.bankjob.model.Publicacion;
import com.tpo.bankjob.model.Skill;
import com.tpo.bankjob.model.TipoTrabajo;
import com.tpo.bankjob.model.builder.PublicacionVOBuilder;
import com.tpo.bankjob.model.repository.EmpresaRepository;
import com.tpo.bankjob.model.utils.PostulacionKeyWrapper;
import com.tpo.bankjob.model.vo.PublicacionVO;
import com.tpo.bankjob.security.RequestTokenService;

@SpringBootTest
class ReporteControllerTests {
	
	@Autowired
	ReporteController reporteController;
	
	@Autowired
	EmpresaRepository empresaRepository;
	
	@Autowired
	Publicacion publicacion;
	
	@Autowired
	Postulante postulante;
	
	@Autowired
	Postulacion postulacion;

	@Test
	public void obtenerPublicacionMasSolicitadaTest() {

		// se registra y logea la empresa para poder agregar una publicacion
		Empresa empresaVO = new Empresa(UUID.randomUUID().toString(),
				"empresa1",
				"1234");
		empresaRepository.saveAndFlush(empresaVO);
		RequestTokenService.setRequestToken(empresaVO.getId());
		
		// se agrega la publicacion
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
		
		publicacion.add(vo);
		
		// Se registra y logea un postulante
		String idPostulante = postulante.register(new Postulante("", 
				"postulanteTest66", 
				"1234",
				"Postu",
				"Lante",
				Instant.now().toDateTime()));
		RequestTokenService.setRequestToken(idPostulante);
		
		// el postulante se postula a la publicacion antes creada
		postulacion.add(new Postulacion(new PostulacionKeyWrapper(idPostulante, vo.getId())));
		
		// se obtiene la unica publicacion con postulacion
		Publicacion r = reporteController.obtenerPublicacionMasSolicitada("112021");
		Assert.assertTrue(Objects.nonNull(r) && r.getId().equalsIgnoreCase(vo.getId()));
	}
	
	@Test
	public void obtenerCategoriasMasSeleccionadasTest() {
		
		// se agrega la empresa
		Empresa empresaVO = new Empresa(UUID.randomUUID().toString(),
				"empresa1",
				"1234");
		empresaRepository.saveAndFlush(empresaVO);
		RequestTokenService.setRequestToken(empresaVO.getId());
		
		// se agregan dos publicacion con categoria Petrolera
		publicacion.add(new PublicacionVOBuilder()
				.setTitulo("NODE JS 100% remoto")
				.setDescripcion("Breve descripcion")
				.setCategoria("Petrolera")
				.setModalidad(Modalidad.FULL_TIME)
				.setTipoTrabajo(TipoTrabajo.REMOTO)
				.setLocacion("Buenos Aires")
				.setSueldoOfrecido(Double.valueOf(100))
				.setFechaVigencia(Instant.now().toDateTime())
				.build());
		publicacion.add(new PublicacionVOBuilder()
				.setTitulo("NODE JS 100% remoto")
				.setDescripcion("Breve descripcion")
				.setCategoria("Petrolera")
				.setModalidad(Modalidad.FULL_TIME)
				.setTipoTrabajo(TipoTrabajo.REMOTO)
				.setLocacion("Buenos Aires")
				.setSueldoOfrecido(Double.valueOf(100))
				.setFechaVigencia(Instant.now().toDateTime())
				.build());
		
		// se agrega una publicacion con categoria Bancaria
		publicacion.add(new PublicacionVOBuilder()
				.setTitulo("NODE JS 100% remoto")
				.setDescripcion("Breve descripcion")
				.setCategoria("Petrolera")
				.setModalidad(Modalidad.FULL_TIME)
				.setTipoTrabajo(TipoTrabajo.REMOTO)
				.setLocacion("Bancaria")
				.setSueldoOfrecido(Double.valueOf(100))
				.setFechaVigencia(Instant.now().toDateTime())
				.build());
		
		// se obtiene la categoria mas seleccionada
		String r = reporteController.obtenerCategoriasMasSeleccionadas(1).get(0);
		Assert.assertTrue(StringUtils.isNotBlank(r) && "Petrolera".equalsIgnoreCase(r));		
	}
	
	@Test
	// publicaciones remotas, part time que menos tareas+skills tiene	
	public void obtenerPublicacionMasAccesibleTest() {
		
		// se agrega la empresa
		Empresa empresaVO = new Empresa(UUID.randomUUID().toString(),
				"empresa1",
				"1234");
		empresaRepository.saveAndFlush(empresaVO);
		RequestTokenService.setRequestToken(empresaVO.getId());
		
		// se agregan publicaciones
		// sin tareas ni skills pero no remoto ni parttime
		PublicacionVO publicacionVO = new PublicacionVOBuilder()
				.setTitulo("NODE JS 100% remoto")
				.setDescripcion("Breve descripcion")
				.setCategoria("Petrolera")
				.setModalidad(Modalidad.FULL_TIME)
				.setTipoTrabajo(TipoTrabajo.PRESENCIAL)
				.setLocacion("Buenos Aires")
				.setSueldoOfrecido(Double.valueOf(100))
				.setFechaVigencia(Instant.now().toDateTime())
				.build();
		publicacion.add(publicacionVO);
		
		// remoto y partime pero con dos skills requeridos
		publicacionVO = new PublicacionVOBuilder()
				.setTitulo("NODE JS 100% remoto")
				.setDescripcion("Breve descripcion")
				.setCategoria("Petrolera")
				.setModalidad(Modalidad.PART_TIME)
				.setTipoTrabajo(TipoTrabajo.REMOTO)
				.setLocacion("Buenos Aires")
				.setSueldoOfrecido(Double.valueOf(100))
				.setFechaVigencia(Instant.now().toDateTime())
				.setSkills(Lists.newArrayList(
						new Skill(null, "Java", true), 
						new Skill(null, "React", true))
						)
				.build();
		publicacion.add(publicacionVO);
		
		// remoto y part time con 1 skill requerido
		publicacionVO = new PublicacionVOBuilder()
				.setTitulo("NODE JS 100% remoto")
				.setDescripcion("Breve descripcion")
				.setCategoria("Petrolera")
				.setModalidad(Modalidad.PART_TIME)
				.setTipoTrabajo(TipoTrabajo.REMOTO)
				.setLocacion("Buenos Aires")
				.setSueldoOfrecido(Double.valueOf(100))
				.setFechaVigencia(Instant.now().toDateTime())
				.setSkills(Lists.newArrayList(
						new Skill(null, "Cobol", true))
						)
				.build();
		publicacion.add(publicacionVO);
		
		// se obtiene la categoria mas seleccionada
		PublicacionVO r = reporteController.obtenerPublicacionMasAccesible();
		Assert.assertTrue(Objects.nonNull(r) && r.getTitulo().equalsIgnoreCase("Publicacion3"));	
	}
	
	@Test
	// publicaciones que mas skills requieren	
	public void obtenerPublicacionMasExigenteTest() {
		
		// se agrega la empresa
		Empresa empresaVO = new Empresa(UUID.randomUUID().toString(),
				"empresa1",
				"1234");
		empresaRepository.saveAndFlush(empresaVO);
		RequestTokenService.setRequestToken(empresaVO.getId());
		
		// se agregan publicaciones
		// sin skills
		PublicacionVO publicacionVO = new PublicacionVOBuilder()
				.setTitulo("NODE JS 100% remoto")
				.setDescripcion("Breve descripcion")
				.setCategoria("Petrolera")
				.setModalidad(Modalidad.PART_TIME)
				.setTipoTrabajo(TipoTrabajo.REMOTO)
				.setLocacion("Buenos Aires")
				.setSueldoOfrecido(Double.valueOf(100))
				.setFechaVigencia(Instant.now().toDateTime())
				.build();
		publicacion.add(publicacionVO);
		
		
		//con 6 skills
		publicacionVO = new PublicacionVOBuilder()
				.setTitulo("Fullstack 100% remoto")
				.setDescripcion("Breve descripcion")
				.setCategoria("Petrolera")
				.setModalidad(Modalidad.PART_TIME)
				.setTipoTrabajo(TipoTrabajo.REMOTO)
				.setLocacion("Buenos Aires")
				.setSueldoOfrecido(Double.valueOf(100))
				.setFechaVigencia(Instant.now().toDateTime())
				.setSkills(Lists.newArrayList(
						new Skill(null, "Cobol", true),
						new Skill(null, "Java", true),
						new Skill(null, "React", true),
						new Skill(null, "Chakra", true),
						new Skill(null, "Javascript", true),
						new Skill(null, "SQL", true))
						)
				.build();
		publicacion.add(publicacionVO);
		
		// con 2 skills
		publicacionVO = new PublicacionVOBuilder()
				.setTitulo("Fullstack 100% remoto")
				.setDescripcion("Breve descripcion")
				.setCategoria("Petrolera")
				.setModalidad(Modalidad.PART_TIME)
				.setTipoTrabajo(TipoTrabajo.REMOTO)
				.setLocacion("Buenos Aires")
				.setSueldoOfrecido(Double.valueOf(100))
				.setFechaVigencia(Instant.now().toDateTime())
				.setSkills(Lists.newArrayList(
						new Skill(null, "Cobol", true),
						new Skill(null, "SQL", true))
						)
				.build();
		publicacion.add(publicacionVO);
		
		// se obtiene la categoria mas seleccionada
		PublicacionVO r = reporteController.obtenerPublicacionMasExigente();
		Assert.assertTrue(Objects.nonNull(r) && r.getTitulo().equalsIgnoreCase("Publicacion2"));
		
	}
}
