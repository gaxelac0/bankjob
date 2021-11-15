package com.tpo.bankjob.model;

import java.util.List;
import java.util.Optional;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;
import com.tpo.bankjob.model.dao.PostulacionDao;
import com.tpo.bankjob.model.utils.PostulacionKeyWrapper;
import com.tpo.bankjob.model.utils.View;

@Component
@Entity
@Table(name = "postulacion")
@JsonRootName(value = "postulacion")
@JsonView(View.ExtendedPublic.class)
public class Postulacion {
	
	@Autowired
	@Transient
	private PostulacionDao postulacionDao;
	

	@JsonView(View.Public.class)
	@EmbeddedId
	private PostulacionKeyWrapper id;
	
	@ManyToOne
    @MapsId("idPostulante")
    @JoinColumn(name = "id_postulante")
	@JsonIgnore
	private Postulante postulante;

    @ManyToOne
    @MapsId("idPublicacion")
    @JoinColumn(name = "id_publicacion")
    @JsonIgnore
    private Publicacion publicacion;
    
    @JsonView(View.Public.class)
    @Column(name =  "remuneracion")
    @JsonProperty("remuneracion")
    private double remuneracion;
    
    @JsonView(View.Public.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ", timezone="America/Buenos Aires")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@JsonProperty("fecha_postulacion")
	@Column(name = "fecha_postulacion")
	private DateTime fechaPostulacion;
    
    @JsonView(View.Public.class)
	@Column(name = "cv")
	@JsonProperty("cv")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String cv;
    
	public Postulacion() {
		this.fechaPostulacion = Instant.now().toDateTime();
	}

	public Postulacion(PostulacionKeyWrapper id) {
		this();
		this.id = id;
	}

	public PostulacionKeyWrapper getId() {
		return id;
	}

	public void setId(PostulacionKeyWrapper id) {
		this.id = id;
	}

	public Postulante getPostulante() {
		return postulante;
	}

	public void setPostulante(Postulante postulante) {
		this.postulante = postulante;
	}

	public Publicacion getPublicacion() {
		return publicacion;
	}

	public void setPublicacion(Publicacion publicacion) {
		this.publicacion = publicacion;
	}

	public String getCv() {
		return cv;
	}

	public void setCv(String cv) {
		this.cv = cv;
	}

	public double getRemuneracion() {
		return remuneracion;
	}

	public void setRemuneracion(double remuneracion) {
		this.remuneracion = remuneracion;
	}

	public DateTime getFechaPostulacion() {
		return fechaPostulacion;
	}

	public void setFechaPostulacion(DateTime fechaPostulacion) {
		this.fechaPostulacion = fechaPostulacion;
	}

	public Postulacion add(Postulacion postulacionVO) throws RuntimeException {
		return postulacionDao.add(postulacionVO);
	}

	public List<Postulacion> findAll() {
		return postulacionDao.findAll();
	}

	public Optional<Postulacion> findById(PostulacionKeyWrapper id) {
		return postulacionDao.findById(id);
	}
}
