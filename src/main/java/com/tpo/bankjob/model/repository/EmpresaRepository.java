package com.tpo.bankjob.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tpo.bankjob.model.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa,String> {
	
	@Query(value = "SELECT * FROM empresa p WHERE LOWER(p.username) = LOWER(:username)", nativeQuery = true)
	public Empresa findByUsername(@Param("username") String username);
	
}
