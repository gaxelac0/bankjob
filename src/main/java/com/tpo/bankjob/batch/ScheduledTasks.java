package com.tpo.bankjob.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tpo.bankjob.model.repository.PublicacionRepository;

@Component
public class ScheduledTasks {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);
	
	@Autowired
	PublicacionRepository publicacionRepository;
	
	@Scheduled(fixedRate = 60000)
    public void transicionarPublicaciones() {
		publicacionRepository.findAll().forEach((p) -> publicacionRepository.saveAndFlush(p.transicionar())); //-- TODO excluir publicaciones finalizadas
    }

}
