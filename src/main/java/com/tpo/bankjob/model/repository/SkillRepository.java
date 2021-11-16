package com.tpo.bankjob.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tpo.bankjob.model.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill,Long> {

	@Query(value = "SELECT * FROM skill s WHERE p.ownerId = :ownerId", nativeQuery = true)
	public List<Skill> findAllByOwnerId(@Param("ownerId") String ownerId);
	
}
