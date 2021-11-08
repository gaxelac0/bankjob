package com.tpo.bankjob.model.vo;

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

@Component
@Entity
@Table(name = "interes")
@JsonRootName(value = "interes")
public class InteresVO implements Serializable {
	
	private static final long serialVersionUID = -2653294078098750944L;

	@Id 
	@GeneratedValue
	@Column(name = "id")
	@JsonIgnore
	private Long id;
	
	@JsonProperty("id_postulante")
	@Column(name = "idPostulante")
	private String idPostulante;
	
	@JsonProperty("categoria")
	@Column(name = "categoria")
	private String categoria;
	
	public InteresVO() {}
	
	public InteresVO(Long id, String categoria) {
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
