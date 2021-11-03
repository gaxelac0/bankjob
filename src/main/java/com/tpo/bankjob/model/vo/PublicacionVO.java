package com.tpo.bankjob.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.tpo.bankjob.model.state.EstadoPublicacion;
import com.tpo.bankjob.model.state.EstadoPublicacionAbierto;

@Component
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
	private String idEmpresa;
	
	@JsonIgnore
	@Column(name = "estado")
	private EstadoPublicacion estado;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ", timezone="America/Buenos Aires")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "fecha_vigencia")
	@JsonProperty("fecha_vigencia")
	private DateTime fechaVigencia;
	
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
	
	public PublicacionVO() {
		this.estado = new EstadoPublicacionAbierto(this);
	}
		
	// constructor with all the fields
	public PublicacionVO(String idEmpresa, String titulo, String descripcion, 
			ModalidadEnum modalidad, TipoTrabajoEnum tipoTrabajo,
			String lugar, String categoria,
			double sueldoOfrecido, DateTime fechaVigencia) {
		this();
		this.idEmpresa = idEmpresa;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.modalidad = modalidad;
		this.tipoTrabajo = tipoTrabajo;
		this.lugar = lugar;
		this.categoria = categoria;
		this.sueldoOfrecido = sueldoOfrecido;
		this.fechaVigencia = fechaVigencia;
	}
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(String idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	
	public EstadoPublicacion getEstado() {
		return estado;
	}

	public void setEstado(EstadoPublicacion estado) {
		this.estado = estado;
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
	
	public boolean isOpen() {
		return (this.estado instanceof EstadoPublicacionAbierto);
	}

	public DateTime getFechaVigencia() {
		return fechaVigencia;
	}

	public void setFechaVigencia(DateTime fechaVigencia) {
		this.fechaVigencia = fechaVigencia;
	}

	public PublicacionVO transicionar() {
		return estado.transicionar(this);
	}
}
