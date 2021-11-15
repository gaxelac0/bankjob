package com.tpo.bankjob.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tpo.bankjob.model.Postulante;

@Repository
public interface PostulanteRepository extends JpaRepository<Postulante,String> {
	
	@Query(value = "SELECT * FROM postulante p WHERE LOWER(p.username) = LOWER(:username)", nativeQuery = true)
	public Postulante findByUsername(@Param("username") String username);

}
