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
import com.tpo.bankjob.model.Postulacion;
import com.tpo.bankjob.model.Postulante;
import com.tpo.bankjob.model.Publicacion;
import com.tpo.bankjob.model.repository.EmpresaRepository;
import com.tpo.bankjob.model.utils.PostulacionKeyWrapper;
import com.tpo.bankjob.model.vo.EmpresaVO;
import com.tpo.bankjob.model.vo.ModalidadEnum;
import com.tpo.bankjob.model.vo.PostulacionVO;
import com.tpo.bankjob.model.vo.PostulanteVO;
import com.tpo.bankjob.model.vo.PublicacionVO;
import com.tpo.bankjob.model.vo.SkillVO;
import com.tpo.bankjob.model.vo.TipoTrabajoEnum;
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
		EmpresaVO empresaVO = new EmpresaVO(UUID.randomUUID().toString(),
				"empresa1",
				"1234");
		empresaRepository.saveAndFlush(empresaVO);
		RequestTokenService.setRequestToken(empresaVO.getId());
		
		// se agrega la publicacion
		PublicacionVO publicacionVO = new PublicacionVO(empresaVO,
				"Titulo", 
				"Descripcion", 
				ModalidadEnum.FULL_TIME, 
				TipoTrabajoEnum.PRESENCIAL, 
				"Lugar",
				"Categoria",
				Double.valueOf(100),
				new DateTime());
		publicacion.add(publicacionVO);
		
		// Se registra y logea un postulante
		String idPostulante = postulante.register(new PostulanteVO("", 
				"postulanteTest44", 
				"1234",
				"Postu",
				"Lante",
				Instant.now().toDateTime()));
		RequestTokenService.setRequestToken(idPostulante);
		
		// el postulante se postula a la publicacion antes creada
		postulacion.add(new PostulacionVO(new PostulacionKeyWrapper(idPostulante, publicacionVO.getId())));
		
		// se obtiene la unica publicacion con postulacion
		PublicacionVO r = reporteController.obtenerPublicacionMasSolicitada("112021");
		Assert.assertTrue(Objects.nonNull(r) && r.getId().equalsIgnoreCase(publicacionVO.getId()));
	}
	
	@Test
	public void obtenerCategoriasMasSeleccionadasTest() {
		
		// se agrega la empresa
		EmpresaVO empresaVO = new EmpresaVO(UUID.randomUUID().toString(),
				"empresa1",
				"1234");
		empresaRepository.saveAndFlush(empresaVO);
		RequestTokenService.setRequestToken(empresaVO.getId());
		
		// se agregan dos publicacion con categoria Petrolera
		publicacion.add(new PublicacionVO(empresaVO,
				"Titulo", 
				"Descripcion", 
				ModalidadEnum.FULL_TIME, 
				TipoTrabajoEnum.PRESENCIAL, 
				"Lugar",
				"Petrolera",
				Double.valueOf(100),
				new DateTime()));
		publicacion.add(new PublicacionVO(empresaVO,
				"Titulo", 
				"Descripcion", 
				ModalidadEnum.FULL_TIME, 
				TipoTrabajoEnum.PRESENCIAL, 
				"Lugar",
				"Petrolera",
				Double.valueOf(100),
				new DateTime()));
		
		// se agrega una publicacion con categoria Bancaria
		publicacion.add(new PublicacionVO(empresaVO,
				"Titulo", 
				"Descripcion", 
				ModalidadEnum.FULL_TIME, 
				TipoTrabajoEnum.PRESENCIAL, 
				"Lugar",
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
		EmpresaVO empresaVO = new EmpresaVO(UUID.randomUUID().toString(),
				"empresa1",
				"1234");
		empresaRepository.saveAndFlush(empresaVO);
		RequestTokenService.setRequestToken(empresaVO.getId());
		
		// se agregan publicaciones
		// sin tareas ni skills pero no remoto ni parttime
		PublicacionVO publicacionVO = new PublicacionVO(empresaVO,
				"Publicacion1", 
				"Descripcion", 
				ModalidadEnum.FULL_TIME, 
				TipoTrabajoEnum.PRESENCIAL, 
				"Lugar",
				"Petrolera",
				Double.valueOf(100),
				new DateTime());
		publicacion.add(publicacionVO);
		
		// remoto y partime pero con dos skills requeridos
		publicacionVO = new PublicacionVO(empresaVO,
				"Publicacion2", 
				"Descripcion", 
				ModalidadEnum.PART_TIME, 
				TipoTrabajoEnum.REMOTO, 
				"Lugar",
				"Petrolera",
				Double.valueOf(100),
				new DateTime());
		publicacionVO.getSkills().add(new SkillVO(null, "Java", true));
		publicacionVO.getSkills().add(new SkillVO(null, "React", true));
		publicacion.add(publicacionVO);
		
		// remoto y part time con 1 skill requerido
		publicacionVO = new PublicacionVO(empresaVO,
				"Publicacion3", 
				"Descripcion", 
				ModalidadEnum.PART_TIME, 
				TipoTrabajoEnum.REMOTO, 
				"Lugar",
				"Petrolera",
				Double.valueOf(100),
				new DateTime());
		publicacionVO.getSkills().add(new SkillVO(null, "Cobol", true));
		publicacion.add(publicacionVO);
		
		// se obtiene la categoria mas seleccionada
		PublicacionVO r = reporteController.obtenerPublicacionMasAccesible();
		Assert.assertTrue(Objects.nonNull(r) && r.getTitulo().equalsIgnoreCase("Publicacion3"));	
	}
	
	@Test
	// publicaciones que mas skills requieren	
	public void obtenerPublicacionMasExigenteTest() {
		
		// se agrega la empresa
		EmpresaVO empresaVO = new EmpresaVO(UUID.randomUUID().toString(),
				"empresa1",
				"1234");
		empresaRepository.saveAndFlush(empresaVO);
		RequestTokenService.setRequestToken(empresaVO.getId());
		
		// se agregan publicaciones
		// sin skills
		PublicacionVO publicacionVO = new PublicacionVO(empresaVO,
				"Publicacion1", 
				"Descripcion", 
				ModalidadEnum.FULL_TIME, 
				TipoTrabajoEnum.PRESENCIAL, 
				"Lugar",
				"Petrolera",
				Double.valueOf(100),
				new DateTime());
		publicacion.add(publicacionVO);
		
		
		//con 6 skills
		publicacionVO = new PublicacionVO(empresaVO,
				"Publicacion2", 
				"Descripcion", 
				ModalidadEnum.PART_TIME, 
				TipoTrabajoEnum.REMOTO, 
				"Lugar",
				"Petrolera",
				Double.valueOf(100),
				new DateTime());
		publicacionVO.getSkills().add(new SkillVO(null, "Java", true));
		publicacionVO.getSkills().add(new SkillVO(null, "React", true));
		publicacionVO.getSkills().add(new SkillVO(null, "Springboot", true));
		publicacionVO.getSkills().add(new SkillVO(null, "Hibernate", true));
		publicacionVO.getSkills().add(new SkillVO(null, "JavaScript", true));
		publicacionVO.getSkills().add(new SkillVO(null, "Chakra", true));
		publicacion.add(publicacionVO);
		
		// con 2 skills
		publicacionVO = new PublicacionVO(empresaVO,
				"Publicacion3", 
				"Descripcion", 
				ModalidadEnum.PART_TIME, 
				TipoTrabajoEnum.REMOTO, 
				"Lugar",
				"Petrolera",
				Double.valueOf(100),
				new DateTime());
		publicacionVO.getSkills().add(new SkillVO(null, "Java", true));
		publicacionVO.getSkills().add(new SkillVO(null, "Cobol", true));
		publicacion.add(publicacionVO);
		
		// se obtiene la categoria mas seleccionada
		PublicacionVO r = reporteController.obtenerPublicacionMasExigente();
		Assert.assertTrue(Objects.nonNull(r) && r.getTitulo().equalsIgnoreCase("Publicacion2"));
		
	}
}
