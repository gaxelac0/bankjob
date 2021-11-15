package com.tpo.bankjob.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tpo.bankjob.model.Postulacion;
import com.tpo.bankjob.model.utils.PostulacionKeyWrapper;

public interface PostulacionRepository extends JpaRepository<Postulacion, PostulacionKeyWrapper> {

}
