package com.tpo.bankjob.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tpo.bankjob.model.vo.InteresVO;

@Repository
public interface InteresRepository extends JpaRepository<InteresVO,Long> {
	
}