package com.tpo.bankjob.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tpo.bankjob.model.utils.PostulacionKeyWrapper;
import com.tpo.bankjob.model.vo.PostulacionVO;

public interface PostulacionRepository extends JpaRepository<PostulacionVO, PostulacionKeyWrapper> {

}
