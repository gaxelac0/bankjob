package com.tpo.bankjob.model.vo;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.tpo.bankjob.model.utils.PostulacionKeyWrapper;

@Component
@Entity
@Table(name = "postulacion")
@JsonRootName(value = "postulacion")
public class PostulacionVO {
	
	@EmbeddedId
	private PostulacionKeyWrapper id;
	
	@ManyToOne
    @MapsId("idPostulante")
    @JoinColumn(name = "id_postulante")
	@JsonIgnore
	private PostulanteVO postulante;

    @ManyToOne
    @MapsId("idPublicacion")
    @JoinColumn(name = "id_publicacion")
    @JsonIgnore
    private PublicacionVO publicacion;
    
    @Column(name =  "cv")
    @JsonProperty("cv")
    private String cv;
    
    @Column(name =  "remuneracion")
    @JsonProperty("remuneracion")
    private double remuneracion;
	
    
	public PostulacionVO() {}

	public PostulacionVO(PostulacionKeyWrapper id) {
		this();
		this.id = id;
	}

	public PostulacionKeyWrapper getId() {
		return id;
	}

	public void setId(PostulacionKeyWrapper id) {
		this.id = id;
	}

	public PostulanteVO getPostulante() {
		return postulante;
	}

	public void setPostulante(PostulanteVO postulante) {
		this.postulante = postulante;
	}

	public PublicacionVO getPublicacion() {
		return publicacion;
	}

	public void setPublicacion(PublicacionVO publicacion) {
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

}
