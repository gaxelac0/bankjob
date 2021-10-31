package com.tpo.bankjob.model.vo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@Entity
@Table(name = "empresa")
@JsonRootName(value = "empresa")
public class EmpresaVO {
	
	@JsonProperty("id")
	private @Id @GeneratedValue Long id;
	
	@JsonProperty("razon_social")
	@Column(name = "razon_social")
	private String razonSocial;
	
	@JsonIgnore
	@OneToMany(mappedBy = "idEmpresa",fetch = FetchType.EAGER)
	private List<PublicacionVO> publicaciones;
	
	public EmpresaVO () {
		this.razonSocial = "";
		this.publicaciones = new ArrayList<>();
	}
		
	public EmpresaVO(String razonSocial) {
		this();
		this.razonSocial = razonSocial;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the razonSocial
	 */
	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public void setPublicaciones(List<PublicacionVO> publicaciones) {
		this.publicaciones = publicaciones;
	}

	/**
	 * @return the publicaciones
	 */
	public List<PublicacionVO> getPublicaciones() {
		return publicaciones;
	}
	
	public void addPublicacion(PublicacionVO publicacion) {
		this.publicaciones.add(publicacion);
	}
	
	public boolean equals(EmpresaVO other) {
		return this.id == other.getId();
	}


}
