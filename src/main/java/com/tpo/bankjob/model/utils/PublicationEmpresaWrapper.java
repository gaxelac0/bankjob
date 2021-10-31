package com.tpo.bankjob.model.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.tpo.bankjob.model.vo.EmpresaVO;
import com.tpo.bankjob.model.vo.PublicacionVO;

@JsonRootName(value = "wrapper")
public class PublicationEmpresaWrapper {
	
	@JsonProperty("empresa")
	public EmpresaVO empresa;
	
	@JsonProperty("publicacion")
	public PublicacionVO publicacion;
}