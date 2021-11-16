package com.tpo.bankjob.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;
import com.tpo.bankjob.model.utils.View;

@Component
@Entity
@Table(name = "interes")
@JsonRootName(value = "interes")
@JsonView(View.Public.class)
public class Interes implements Serializable {
	
	private static final long serialVersionUID = -2653294078098750944L;

	@Id 
	@GeneratedValue
	@Column(name = "id")
	@JsonIgnore
	private Long id;
	
	@JsonView(View.Internal.class)
	@JsonProperty("id_postulante")
	@Column(name = "idPostulante")
	private String idPostulante;
	
	@JsonView(View.Public.class)
	@JsonProperty("categoria")
	@Column(name = "categoria")
	private String categoria;
	
	public Interes() {}
	
	public Interes(Long id, String categoria) {
		this();
		this.id = id;
		this.categoria = categoria;
	}
	
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	public String getIdPostulante() {
		return idPostulante;
	}
	public void setIdPostulante(String idPostulante) {
		this.idPostulante = idPostulante;
	}
}
