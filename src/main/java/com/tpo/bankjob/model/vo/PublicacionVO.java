package com.tpo.bankjob.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@Entity
@Table(name = "publicacion")
@JsonRootName(value = "publicacion")
public class PublicacionVO implements Serializable {
	
	private static final long serialVersionUID = 6672245192915168413L;

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@JsonProperty("id")
	private Long id;
	
	@Column(name = "id_empresa")
	@JsonProperty("id_empresa")
	private Long idEmpresa;
	
	@Column(name = "titulo")
	@JsonProperty("titulo")
	private String titulo;
	
	@Column(name = "descripcion")
	@JsonProperty("descripcion")
	private String descripcion;
	
	@Column(name = "modalidad")
	@JsonProperty("modalidad")
	private ModalidadEnum modalidad;
	
	@Column(name = "tipo_trabajo")
	@JsonProperty("tipo_trabajo")
	private TipoTrabajoEnum tipoTrabajo;
	
	@Column(name = "lugar")
	@JsonProperty("lugar")
	private String lugar;
	
	@Column(name = "categoria")
	@JsonProperty("categoria")
	private String categoria;
	
	@Column(name = "sueldo_ofrecido")
	@JsonProperty("sueldo_ofrecido")
	private double sueldoOfrecido;
	
	@SuppressWarnings("unused")
	public PublicacionVO() {}
		
	// constructor with all the fields
	public PublicacionVO(Long idEmpresa, String titulo, String descripcion, ModalidadEnum modalidad, TipoTrabajoEnum tipoTrabajo,
			String lugar, String categoria, double sueldoOfrecido) {
		super();
		this.idEmpresa = idEmpresa;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.modalidad = modalidad;
		this.tipoTrabajo = tipoTrabajo;
		this.lugar = lugar;
		this.categoria = categoria;
		this.sueldoOfrecido = sueldoOfrecido;
	}
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public ModalidadEnum getModalidad() {
		return modalidad;
	}
	public void setModalidad(ModalidadEnum modalidad) {
		this.modalidad = modalidad;
	}
	public TipoTrabajoEnum getTipoTrabajo() {
		return tipoTrabajo;
	}
	public void setTipoTrabajo(TipoTrabajoEnum tipoTrabajo) {
		this.tipoTrabajo = tipoTrabajo;
	}
	public String getLugar() {
		return lugar;
	}
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public double getSueldoOfrecido() {
		return sueldoOfrecido;
	}
	public void setSueldoOfrecido(double sueldoOfrecido) {
		this.sueldoOfrecido = sueldoOfrecido;
	}
	
	public boolean equals(PublicacionVO other) {
		return this.id == other.getId();
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	
}
