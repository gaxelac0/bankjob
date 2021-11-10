package com.tpo.bankjob;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tpo.bankjob.model.Postulacion;
import com.tpo.bankjob.model.Postulante;
import com.tpo.bankjob.model.Publicacion;
import com.tpo.bankjob.model.exception.InsufficientSkillsForPostulacionException;
import com.tpo.bankjob.model.repository.EmpresaRepository;
import com.tpo.bankjob.model.repository.PublicacionRepository;
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
public class PostulacionControllerTest {
	
    @Autowired
    Publicacion publicacion;
    
    @Autowired
    Postulacion postulacion;
    
    @Autowired
    Postulante postulante;
    
    @Autowired
    EmpresaRepository empresaRepository;
    
    @Autowired
    PublicacionRepository publicacionRepository;
		
	@Test
	public void givenMatchingSkillsPostulanteAndPublicacionWhenAddPostulacionThenPostularExitosamente() {
		
		// given
		EmpresaVO empresaVO = new EmpresaVO(UUID.randomUUID().toString(),
				"empresa1",
				"1234");
		RequestTokenService.setRequestToken(empresaVO.getId());
		empresaRepository.saveAndFlush(empresaVO);
		
		PublicacionVO publicacionVO = new PublicacionVO(empresaVO,
				"Publicacion1", 
				"Descripcion", 
				ModalidadEnum.FULL_TIME, 
				TipoTrabajoEnum.PRESENCIAL, 
				"Lugar",
				"Categoria",
				Double.valueOf(100),
				new DateTime());
		
		//  when
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
		PostulacionKeyWrapper id = new PostulacionKeyWrapper(idPostulante, publicacionVO.getId());
		postulacion.add(new PostulacionVO(id));
		
		// se obtiene la unica publicacion con postulacion
		Optional<PostulacionVO> optPostu = postulacion.findById(id);
		Assert.assertTrue(Objects.nonNull(optPostu.get()));
	}
	
	@Test
	public void givenNotMatchingSkillsPostulanteAndPublicacionWhenAddPostulacionThenPostularExitosamente() {
		
		// given
		EmpresaVO empresaVO = new EmpresaVO(UUID.randomUUID().toString(),
				"empresa1",
				"1234");
		RequestTokenService.setRequestToken(empresaVO.getId());
		empresaRepository.saveAndFlush(empresaVO);
		
		PublicacionVO publicacionVO = new PublicacionVO(empresaVO,
				"Publicacion1", 
				"Descripcion", 
				ModalidadEnum.FULL_TIME, 
				TipoTrabajoEnum.PRESENCIAL, 
				"Lugar",
				"Categoria",
				Double.valueOf(100),
				new DateTime());
		publicacionVO.getSkills().add(new SkillVO(null, "Java", true)); // required skill
		
		//  when
		publicacion.add(publicacionVO);
		
		// Se registra y logea un postulante sin skills
		String idPostulante = postulante.register(new PostulanteVO("", 
				"postulanteTest44", 
				"1234",
				"Postu",
				"Lante",
				Instant.now().toDateTime()));
		RequestTokenService.setRequestToken(idPostulante);
		
		// el postulante se postula a la publicacion antes creada
		PostulacionKeyWrapper id = new PostulacionKeyWrapper(idPostulante, publicacionVO.getId());	
	    Assert.assertThrows(InsufficientSkillsForPostulacionException.class, () -> { 
        		postulacion.add(new PostulacionVO(id)); 
        	}
        );
	}
}
