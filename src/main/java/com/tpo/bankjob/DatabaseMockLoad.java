package com.tpo.bankjob;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tpo.bankjob.model.Empresa;
import com.tpo.bankjob.model.Postulante;
import com.tpo.bankjob.model.repository.EmpresaRepository;
import com.tpo.bankjob.model.repository.PostulanteRepository;
import com.tpo.bankjob.model.vo.CanalNotificacion;
import com.tpo.bankjob.security.UserCrudService;

@Configuration
public class DatabaseMockLoad {

	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseMockLoad.class);
	
	@Autowired
	UserCrudService users;

	@Bean
	CommandLineRunner initDatabase(PostulanteRepository postulanteRepository, EmpresaRepository empresaRepository) {

		return args -> {
			
			// TODO: cargar memoria
			//////////////////////////// >>> Postulante
			Postulante postulanteVO = new Postulante("postulante1", "postulante1", "1234",
					"Postu", "Lante", new DateTime(2021, 11, 1, 0, 0));
			users.save(postulanteVO.getId(), postulanteVO);
			LOGGER.info("Preloading " + postulanteRepository.saveAndFlush(postulanteVO));
	
			////////////////////////////>>> Empresa
			Empresa empresaVO = new Empresa("empresa1", "empresa1", "1234");
			empresaVO.setCanalNotificacion(CanalNotificacion.MAIL);
			users.save(empresaVO.getId(), empresaVO);
			LOGGER.info("Preloading " + empresaRepository.saveAndFlush(empresaVO));	
		};
	}
}
