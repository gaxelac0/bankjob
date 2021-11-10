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
	public void givenEmpresaYPublicacionVoValidasWhenAddPublicacionThenGuardarlaExitosamente() {
		
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
	
	
	@Test
	public void givenPublicacionValidaSinTituloWhenSaveThenGuardarlaExistosamenteConTituloAutogenerado() {
		
		// given
		EmpresaVO empresaVO = new EmpresaVO(UUID.randomUUID().toString(),
				"empresa1",
				"1234");
		RequestTokenService.setRequestToken(empresaVO.getId());
		empresaRepository.saveAndFlush(empresaVO);
		
		PublicacionVO publicacionVO = new PublicacionVO(empresaVO,
				"", 
				"Descripcion", 
				ModalidadEnum.FULL_TIME, 
				TipoTrabajoEnum.PRESENCIAL, 
				"Lugar",
				"Categoria",
				Double.valueOf(100),
				new DateTime());
		
		//  when
		publicacion.add(publicacionVO);
		
		// then
		PublicacionVO resultPublicacion = null;
		Optional<PublicacionVO> optResultPublicacion = publicacionRepository.findById(publicacionVO.getId());
	
		if(optResultPublicacion.isPresent())
			resultPublicacion = optResultPublicacion.get();
		
		Assert.assertNotNull(resultPublicacion);
		Assert.assertTrue(!resultPublicacion.getTitulo().isBlank() 
				&& resultPublicacion.getTitulo().contains(resultPublicacion.getLugar()));
		
	}
}
