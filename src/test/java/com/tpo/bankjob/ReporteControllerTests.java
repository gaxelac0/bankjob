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

import com.tpo.bankjob.controller.ReporteController;
import com.tpo.bankjob.model.Empresa;
import com.tpo.bankjob.model.Modalidad;
import com.tpo.bankjob.model.Postulacion;
import com.tpo.bankjob.model.Postulante;
import com.tpo.bankjob.model.Publicacion;
import com.tpo.bankjob.model.Skill;
import com.tpo.bankjob.model.TipoTrabajo;
import com.tpo.bankjob.model.repository.EmpresaRepository;
import com.tpo.bankjob.model.utils.PostulacionKeyWrapper;
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
		Empresa empresa = new Empresa(UUID.randomUUID().toString(),
				"empresa12",
				"1234");
		empresaRepository.saveAndFlush(empresa);
		RequestTokenService.setRequestToken(empresa.getId());
		
		// se agrega la publicacion
		Publicacion pub = new Publicacion(empresa,
				"Titulo", 
				"Descripcion", 
				Modalidad.FULL_TIME, 
				TipoTrabajo.PRESENCIAL, 
				"Locacion",
				"Categoria",
				Double.valueOf(100),
				new DateTime(2030,11,1,1,1));
		
		publicacion.add(pub);
			
		// Se registran y logean nuevos postulantes
		registrarYPostularPostulante("postulanteTest449", pub);
		registrarYPostularPostulante("postulanteTest450", pub);
		registrarYPostularPostulante("postulanteTest451", pub);
		registrarYPostularPostulante("postulanteTest452", pub);
		registrarYPostularPostulante("postulanteTest453", pub);
		registrarYPostularPostulante("postulanteTest454", pub);
				
		// se obtiene la unica publicacion con postulacion
		Publicacion r = reporteController.obtenerPublicacionMasSolicitada("112021");		
		Assert.assertTrue(Objects.nonNull(r) && r.getId().equalsIgnoreCase(pub.getId()));
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
		publicacion.add(new Publicacion(empresaVO,
				"Titulo", 
				"Descripcion", 
				Modalidad.FULL_TIME, 
				TipoTrabajo.PRESENCIAL, 
				"Locacion",
				"Petrolera",
				Double.valueOf(100),
				new DateTime()));
		publicacion.add(new Publicacion(empresaVO,
				"Titulo", 
				"Descripcion", 
				Modalidad.FULL_TIME, 
				TipoTrabajo.PRESENCIAL, 
				"Locacion",
				"Petrolera",
				Double.valueOf(100),
				new DateTime()));
		
		// se agrega una publicacion con categoria Bancaria
		publicacion.add(new Publicacion(empresaVO,
				"Titulo", 
				"Descripcion", 
				Modalidad.FULL_TIME, 
				TipoTrabajo.PRESENCIAL, 
				"Locacion",
				"Bancaria",
				Double.valueOf(100),
				new DateTime()));
		
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
		Publicacion pub = new Publicacion(empresaVO,
				"Publicacion1", 
				"Descripcion", 
				Modalidad.FULL_TIME, 
				TipoTrabajo.PRESENCIAL, 
				"Locacion",
				"Petrolera",
				Double.valueOf(100),
				new DateTime());
		publicacion.add(pub);
		
		// remoto y partime pero con dos skills requeridos
		pub = new Publicacion(empresaVO,
				"Publicacion2", 
				"Descripcion", 
				Modalidad.PART_TIME, 
				TipoTrabajo.REMOTO, 
				"Locacion",
				"Petrolera",
				Double.valueOf(100),
				new DateTime());
		pub.getSkills().add(new Skill(null, "Java", true));
		pub.getSkills().add(new Skill(null, "React", true));
		publicacion.add(pub);
		
		// remoto y part time con 1 skill requerido
		pub = new Publicacion(empresaVO,
				"Publicacion3", 
				"Descripcion", 
				Modalidad.PART_TIME, 
				TipoTrabajo.REMOTO, 
				"Locacion",
				"Petrolera",
				Double.valueOf(100),
				new DateTime());
		pub.getSkills().add(new Skill(null, "Cobol", true));
		publicacion.add(pub);
		
		// se obtiene la categoria mas seleccionada
		Publicacion r = reporteController.obtenerPublicacionMasAccesible();
		Assert.assertTrue(Objects.nonNull(r) && r.getTitulo().equalsIgnoreCase("Publicacion3"));	
	}
	
	@Test
	// publicaciones que mas skills requieren	
	public void obtenerPublicacionMasExigenteTest() {
		
		// se agrega la empresa
		Empresa empresa = new Empresa(UUID.randomUUID().toString(),
				"empresa1",
				"1234");
		empresaRepository.saveAndFlush(empresa);
		RequestTokenService.setRequestToken(empresa.getId());
		
		// se agregan publicaciones
		// sin skills
		Publicacion pub = new Publicacion(empresa,
				"Publicacion1", 
				"Descripcion", 
				Modalidad.FULL_TIME, 
				TipoTrabajo.PRESENCIAL, 
				"Locacion",
				"Petrolera",
				Double.valueOf(100),
				new DateTime());
		publicacion.add(pub);
		
		
		//con 6 skills
		pub = new Publicacion(empresa,
				"Publicacion2", 
				"Descripcion", 
				Modalidad.PART_TIME, 
				TipoTrabajo.REMOTO, 
				"Locacion",
				"Petrolera",
				Double.valueOf(100),
				new DateTime());
		pub.getSkills().add(new Skill(null, "Java", true));
		pub.getSkills().add(new Skill(null, "React", true));
		pub.getSkills().add(new Skill(null, "Springboot", true));
		pub.getSkills().add(new Skill(null, "Hibernate", true));
		pub.getSkills().add(new Skill(null, "JavaScript", true));
		pub.getSkills().add(new Skill(null, "Chakra", true));
		publicacion.add(pub);
		
		// con 2 skills
		pub = new Publicacion(empresa,
				"Publicacion3", 
				"Descripcion", 
				Modalidad.PART_TIME, 
				TipoTrabajo.REMOTO, 
				"Locacion",
				"Petrolera",
				Double.valueOf(100),
				new DateTime());
		pub.getSkills().add(new Skill(null, "Java", true));
		pub.getSkills().add(new Skill(null, "Cobol", true));
		publicacion.add(pub);
		
		// se obtiene la categoria mas seleccionada
		Publicacion r = reporteController.obtenerPublicacionMasExigente();
		Assert.assertTrue(Objects.nonNull(r) && r.getTitulo().equalsIgnoreCase("Publicacion2"));
		
	}
	
	/**
	 * Registra un usuario postulante y lo postula a la publicacion
	 * @param userid
	 * @param pub
	 */
	private void registrarYPostularPostulante(String userid, Publicacion pub) {
		String idPostulante = postulante.register(new Postulante("", 
				userid, 
				"1234",
				"Postu",
				"Lante",
				Instant.now().toDateTime()));
		RequestTokenService.setRequestToken(idPostulante);
		
		// el postulante se postula a la publicacion antes creada
		postulacion.add(new Postulacion(new PostulacionKeyWrapper(idPostulante, pub.getId())));
	}
}
