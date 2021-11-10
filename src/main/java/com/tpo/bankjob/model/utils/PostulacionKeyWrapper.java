package com.tpo.bankjob.model.utils;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;

@Component
@Embeddable
@JsonRootName(value = "id")
@JsonView(View.Public.class)
public class PostulacionKeyWrapper implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -116116459282227011L;

	@JsonView(View.Public.class)
	@Column(name = "id_postulante")
	@JsonProperty("id_postulante")
	private String idPostulante;
	
	@JsonView(View.Public.class)
	@Column(name = "id_publicacion")
	@JsonProperty("id_publicacion")
	private String idPublicacion;
	
	public PostulacionKeyWrapper() {}

	public PostulacionKeyWrapper(String idPostulante, String idPublicacion) {
		this.idPostulante = idPostulante;
		this.idPublicacion = idPublicacion;
	}

	public String getIdPostulante() {
		return idPostulante;
	}

	public void setIdPostulante(String idPostulante) {
		this.idPostulante = idPostulante;
	}

	public String getIdPublicacion() {
		return idPublicacion;
	}

	public void setIdPublicacion(String idPublicacion) {
		this.idPublicacion = idPublicacion;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idPostulante, idPublicacion);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PostulacionKeyWrapper other = (PostulacionKeyWrapper) obj;
		return Objects.equals(idPostulante, other.idPostulante) && Objects.equals(idPublicacion, other.idPublicacion);
	}
}
