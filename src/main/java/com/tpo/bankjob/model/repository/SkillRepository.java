package com.tpo.bankjob.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tpo.bankjob.model.vo.SkillVO;

@Repository
public interface SkillRepository extends JpaRepository<SkillVO,Long> {

	@Query(value = "SELECT * FROM skill s WHERE p.ownerId = :ownerId", nativeQuery = true)
	public List<SkillVO> findAllByOwnerId(@Param("ownerId") String ownerId);
	
}
