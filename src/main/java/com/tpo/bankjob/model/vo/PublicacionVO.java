package com.tpo.bankjob.model.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.tpo.bankjob.model.observer.IObservable;
import com.tpo.bankjob.model.state.EstadoPublicacion;
import com.tpo.bankjob.model.state.EstadoPublicacionAbierto;
import com.tpo.bankjob.model.state.EstadoPublicacionCerrado;

@Component
@Entity
@Table(name = "publicacion")
@JsonRootName(value = "publicacion")
public class PublicacionVO implements Serializable, IObservable {
	
	private static final long serialVersionUID = 6672245192915168413L;

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@JsonProperty("id")
	private String id;
	
	@ManyToOne
    @JoinColumn(name="id_empresa", nullable=false)
	@JsonProperty(value = "id_empresa")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@JsonIdentityReference(alwaysAsId = true) 
	private EmpresaVO empresa;
	
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
	
	@JsonProperty("postulaciones")
    @OneToMany(mappedBy = "publicacion")
    private List<PostulacionVO> postulaciones;
    
	@JsonProperty("skills")
	@Column(name = "skills")	
    @OneToMany(mappedBy = "ownerId")
	@LazyCollection(LazyCollectionOption.FALSE)
	private  List<SkillVO> skills;
	
	@JsonProperty("tareas")
	@Column(name = "tareas")	
    @OneToMany(mappedBy = "publicacion")
	@LazyCollection(LazyCollectionOption.FALSE)
	private  List<TareaVO> tareas;
	
	//@JsonInclude(JsonInclude.Include.NON_NULL)
	//@Transient
	
	// TODO no se estan persistiendo en la base
    //private List<IObserver> observers;
	
	public PublicacionVO() {
		this.estado = new EstadoPublicacionAbierto(this);
		this.postulaciones = new ArrayList<>();
		this.skills = new ArrayList<>();
		this.tareas = new ArrayList<>();
	}

	public PublicacionVO(EmpresaVO empresa, String titulo, String descripcion, 
			ModalidadEnum modalidad, TipoTrabajoEnum tipoTrabajo,
			String lugar, String categoria,
			double sueldoOfrecido, DateTime fechaVigencia) {
		this();
		this.empresa = empresa;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.modalidad = modalidad;
		this.tipoTrabajo = tipoTrabajo;
		this.lugar = lugar;
		this.categoria = categoria;
		this.sueldoOfrecido = sueldoOfrecido;
		this.fechaVigencia = fechaVigencia;
	}
	
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public EmpresaVO getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaVO empresa) {
		this.empresa = empresa;
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
	
	@JsonIgnore
	public boolean isClosed() {
		return (this.estado instanceof EstadoPublicacionCerrado);
	}

	public DateTime getFechaVigencia() {
		return fechaVigencia;
	}

	public void setFechaVigencia(DateTime fechaVigencia) {
		this.fechaVigencia = fechaVigencia;
	}

	public List<PostulacionVO> getPostulaciones() {
		return postulaciones;
	}

	public void setPostulaciones(List<PostulacionVO> postulaciones) {
		this.postulaciones = postulaciones;
	}

	public List<SkillVO> getSkills() {
		return skills;
	}

	public void setSkills(List<SkillVO> skills) {
		this.skills = skills;
	}

	public List<TareaVO> getTareas() {
		return tareas;
	}

	public void setTareas(List<TareaVO> tareas) {
		this.tareas = tareas;
	}

	public PublicacionVO transicionar() {
		return estado.transicionar(this);
	}

	@Override
	public void notificarPostulacion() {
		empresa.notificarPostulacion(this);
	}
}
