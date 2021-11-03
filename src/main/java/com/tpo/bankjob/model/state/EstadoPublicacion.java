package com.tpo.bankjob.model.state;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tpo.bankjob.model.vo.PublicacionVO;

public abstract class EstadoPublicacion implements Serializable {
	
	private static final long serialVersionUID = -897034049431481002L;
	
	public PublicacionVO ctx;
	
	public EstadoPublicacion(PublicacionVO ctx) {}
	public abstract PublicacionVO transicionar(PublicacionVO ctx);

}
