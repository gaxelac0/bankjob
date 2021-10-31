package com.tpo.bankjob;

import java.util.Arrays;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tpo.bankjob.model.repository.EmpresaRepository;
import com.tpo.bankjob.model.repository.PostulanteRepository;
import com.tpo.bankjob.model.vo.EmpresaVO;
import com.tpo.bankjob.model.vo.PostulanteVO;

@Configuration
public class DatabaseMockLoad {

	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseMockLoad.class);

	@Bean
	CommandLineRunner initDatabase(PostulanteRepository repository, EmpresaRepository repository2) {

		return args -> {
			
			// TODO: cargar memoria

			//////////////////////////// >>> Postulante
			PostulanteVO postulante = new PostulanteVO("Pedro", "Sanchez", new Date(1996,10,16),
					Arrays.asList("Argentino"),
					Arrays.asList("Espanhol"),
					Arrays.asList("Bancario"));

			PostulanteVO result = repository.saveAndFlush(postulante);
			LOGGER.info("Preloading " + result);
			
			
			////////////////////////////>>> Empresa
			EmpresaVO empresa = new EmpresaVO("Empresa 100% Real");

			EmpresaVO result2 = repository2.saveAndFlush(empresa);
			LOGGER.info("Preloading " + result2);
		};
	}
}
