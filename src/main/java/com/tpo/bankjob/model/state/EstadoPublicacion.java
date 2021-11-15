package com.tpo.bankjob.model.state;

import java.io.Serializable;

import com.tpo.bankjob.model.Publicacion;

//(#ADOO) PATTERN STATE
public abstract class EstadoPublicacion implements Serializable {
	
	private static final long serialVersionUID = -897034049431481002L;
	
	public Publicacion ctx;
	
	public EstadoPublicacion(Publicacion ctx) {}
	public abstract Publicacion transicionar(Publicacion ctx);
	public abstract void open(Publicacion ctx);

}
