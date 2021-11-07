package com.tpo.bankjob;

import java.util.Optional;
import java.util.UUID;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tpo.bankjob.model.Publicacion;
import com.tpo.bankjob.model.repository.EmpresaRepository;
import com.tpo.bankjob.model.repository.PublicacionRepository;
import com.tpo.bankjob.model.vo.EmpresaVO;
import com.tpo.bankjob.model.vo.ModalidadEnum;
import com.tpo.bankjob.model.vo.PublicacionVO;
import com.tpo.bankjob.model.vo.TipoTrabajoEnum;
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
	public void givenValidEmpresaAndPublicacionVoWhenAddPublicacionThenSaveItSucessfully() {
		
		// given
		EmpresaVO empresaVO = new EmpresaVO(UUID.randomUUID().toString(), "empresa1", "1234");
		RequestTokenService.setRequestToken(empresaVO.getId()); // TODO corregir test? token?
		
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
		publicacion.add(publicacionVO); // TODO corregir test? token?
		
		// then
		EmpresaVO resultEmpresa = null;
		PublicacionVO resultPublicacion = null;
		
		Optional<EmpresaVO> optResultEmpresa = empresaRepository.findById(empresaVO.getId());
		Optional<PublicacionVO> optResultPublicacion = publicacionRepository.findById(publicacionVO.getId());
		
		if(optResultEmpresa.isPresent())
			resultEmpresa = optResultEmpresa.get();
		
		if(optResultPublicacion.isPresent())
			resultPublicacion = optResultPublicacion.get();
		
		Assert.assertNotNull(resultEmpresa);
		Assert.assertNotNull(resultPublicacion);
		Assert.assertTrue(empresaRepository.findById(empresaVO.getId()).get().getPublicaciones().get(0).equals(resultPublicacion));
		Assert.assertTrue(resultPublicacion.getEmpresa().getId().equals(empresaVO.getId()));
	}
}
