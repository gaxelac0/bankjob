package com.tpo.bankjob.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tpo.bankjob.model.vo.EmpresaVO;

@Repository
public interface EmpresaRepository extends JpaRepository<EmpresaVO,String> {
	
	@Query(value = "SELECT * FROM empresa p WHERE LOWER(p.username) = LOWER(:username)", nativeQuery = true)
	public EmpresaVO findByUsername(@Param("username") String username);
	
}
