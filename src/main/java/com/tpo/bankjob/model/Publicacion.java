package com.tpo.bankjob.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.tpo.bankjob.model.dao.PublicacionDao;
import com.tpo.bankjob.model.observer.IObservable;
import com.tpo.bankjob.model.state.EstadoPublicacion;
import com.tpo.bankjob.model.state.EstadoPublicacionAbierto;
import com.tpo.bankjob.model.state.EstadoPublicacionCerrado;
import com.tpo.bankjob.model.utils.View;

@Component
@Entity
@Table(name = "publicacion")
@JsonRootName(value = "publicacion")
@JsonView(View.Public.class)
public class Publicacion implements Serializable, IObservable {
	
	private static final long serialVersionUID = 6672245192915168413L;
	
	@Autowired
	@Transient
	private PublicacionDao publicacionDao;

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@JsonProperty("id")
	private String id;
	
	@JsonView(View.Public.class)
	@ManyToOne
    @JoinColumn(name="id_empresa", nullable=false)
	@JsonProperty(value = "id_empresa")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@JsonIdentityReference(alwaysAsId = true) 
	private Empresa empresa;
	
	@JsonIgnore
	@Column(name = "estado")
	private EstadoPublicacion estado;
	
	@JsonView(View.Public.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ", timezone="America/Buenos Aires")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "fecha_vigencia")
	@JsonProperty("fecha_vigencia")
	private DateTime fechaVigencia;
	
	@JsonView(View.Public.class)
	@Column(name = "titulo")
	@JsonProperty("titulo")
	private String titulo;
	
	@JsonView(View.Public.class)
	@Column(name = "descripcion")
	@JsonProperty("descripcion")
	private String descripcion;
	
	@JsonView(View.Public.class)
	@Column(name = "modalidad")
	@JsonProperty("modalidad")
	private Modalidad modalidad;
	
	@JsonView(View.Public.class)
	@Column(name = "tipo_trabajo")
	@JsonProperty("tipo_trabajo")
	private TipoTrabajo tipoTrabajo;
	
	@JsonView(View.Public.class)
	@Column(name = "locacion")
	@JsonProperty("locacion")
	private String locacion;
	
	@JsonView(View.Public.class)
	@Column(name = "categoria")
	@JsonProperty("categoria")
	private String categoria;
	
	@JsonView(View.Public.class)
	@Column(name = "sueldo_ofrecido")
	@JsonProperty("sueldo_ofrecido")
	private double sueldoOfrecido;
	
	@JsonView(View.ExtendedPublic.class)
	@JsonProperty("postulaciones")
    @OneToMany(mappedBy = "publicacion")
    private List<Postulacion> postulaciones;
    
	@JsonView(View.Public.class)
	@JsonProperty("skills")
	@Column(name = "skills")	
    @OneToMany(mappedBy = "ownerId")
	@LazyCollection(LazyCollectionOption.FALSE)
	private  List<Skill> skills;
	
	@JsonView(View.Public.class)
	@JsonProperty("tareas")
	@Column(name = "tareas")	
    @OneToMany(mappedBy = "publicacion")
	@LazyCollection(LazyCollectionOption.FALSE)
	private  List<Tarea> tareas;
	
	@JsonView(View.Public.class)
	@Column(name = "img")
	@JsonProperty("img")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String img;
		
	public Publicacion() {
		this.estado = new EstadoPublicacionAbierto(this);
		this.postulaciones = new ArrayList<>();
		this.skills = new ArrayList<>();
		this.tareas = new ArrayList<>();
	}

	public Publicacion(Empresa empresa, String titulo, String descripcion, 
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
	
	@JsonView(View.Public.class)
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

	public Publicacion transicionar() {
		return estado.transicionar(this);
	}

	@Override
	public void notificarPostulacion() {
		empresa.notificarPostulacion(this);
	}
	
	public Publicacion add(Publicacion publicacionVO) {		
		return publicacionDao.add(publicacionVO);
	}

	public Optional<Publicacion> get(String id) {
		return publicacionDao.get(id);
	}

	public List<Publicacion> findAll() {
		return publicacionDao.findAll();
	}

	public Publicacion open(Publicacion publicacionVO) {
		return publicacionDao.open(publicacionVO);
	}
		
	public void transicionarPublicaciones() {
		publicacionDao.transicionarPublicaciones();
	}
}
