package com.tpo.bankjob.model.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.tpo.bankjob.model.Empresa;
import com.tpo.bankjob.model.Modalidad;
import com.tpo.bankjob.model.Postulacion;
import com.tpo.bankjob.model.Publicacion;
import com.tpo.bankjob.model.Skill;
import com.tpo.bankjob.model.Tarea;
import com.tpo.bankjob.model.TipoTrabajo;

@JsonRootName(value = "publicacion")
public class PublicacionVO implements Serializable {
	
	private static final long serialVersionUID = 6672245192915168413L;
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty(value = "id_empresa")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@JsonIdentityReference(alwaysAsId = true) 
	private Empresa empresa;
	
	@JsonProperty("open")
	private boolean open;
		
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ", timezone="America/Buenos Aires")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@JsonProperty("fecha_vigencia")
	private DateTime fechaVigencia;
	
	@JsonProperty("titulo")
	private String titulo;
	
	@JsonProperty("descripcion")
	private String descripcion;

	@JsonProperty("modalidad")
	private Modalidad modalidad;
	
	@JsonProperty("tipo_trabajo")
	private TipoTrabajo tipoTrabajo;
	
	@JsonProperty("locacion")
	private String locacion;
	
	@JsonProperty("categoria")
	private String categoria;

	@JsonProperty("sueldo_ofrecido")
	private double sueldoOfrecido;
	
	@JsonProperty("postulaciones")
    private List<Postulacion> postulaciones;
    
	@JsonProperty("skills")
	private List<Skill> skills;
	
	@JsonProperty("tareas")
	private List<Tarea> tareas;
	
	@JsonProperty("img")
	private String img;

		
	public PublicacionVO() {
		this.postulaciones = new ArrayList<>();
		this.skills = new ArrayList<>();
		this.tareas = new ArrayList<>();
	}

	public PublicacionVO(Empresa empresa, String titulo, String descripcion, 
			Modalidad modalidad, TipoTrabajo tipoTrabajo,
			String locacion, String categoria,
			double sueldoOfrecido, DateTime fechaVigencia) {
		this();
		this.empresa = empresa;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.modalidad = modalidad;
		this.tipoTrabajo = tipoTrabajo;
		this.locacion = locacion;
		this.categoria = categoria;
		this.sueldoOfrecido = sueldoOfrecido;
		this.fechaVigencia = fechaVigencia;
	}
	
	public boolean isOpen() {
		return this.open;
	}
	
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
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
	public Modalidad getModalidad() {
		return modalidad;
	}
	public void setModalidad(Modalidad modalidad) {
		this.modalidad = modalidad;
	}
	public TipoTrabajo getTipoTrabajo() {
		return tipoTrabajo;
	}
	public void setTipoTrabajo(TipoTrabajo tipoTrabajo) {
		this.tipoTrabajo = tipoTrabajo;
	}
	public String getLocacion() {
		return locacion;
	}
	public void setLocacion(String locacion) {
		this.locacion = locacion;
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
	
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public boolean equals(Publicacion other) {
		return this.id == other.getId();
	}
	
	public void setOpen(boolean open) {
		this.open = open;
	}
	
	public DateTime getFechaVigencia() {
		return fechaVigencia;
	}

	public void setFechaVigencia(DateTime fechaVigencia) {
		this.fechaVigencia = fechaVigencia;
	}

	public List<Postulacion> getPostulaciones() {
		return postulaciones;
	}

	public void setPostulaciones(List<Postulacion> postulaciones) {
		this.postulaciones = postulaciones;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

	public List<Tarea> getTareas() {
		return tareas;
	}

	public void setTareas(List<Tarea> tareas) {
		this.tareas = tareas;
	}
}
