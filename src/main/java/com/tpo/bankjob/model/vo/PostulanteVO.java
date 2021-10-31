package com.tpo.bankjob.model.vo;

import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PostulanteVO {
	
	private @Id @GeneratedValue Long id;
	
	private String nombre;
	private String apellido;
	private Date fechaNacimiento;
	
	@ElementCollection
	private List<String> nacionalidades;
	
	@ElementCollection
	private List<String> idiomas;
	
	@ElementCollection
	private List<String> intereses;
	
	public PostulanteVO() {}
	
	public PostulanteVO(String nombre, String apellido, 
			Date fechaNacimiento, List<String> nacionalidades,
			List<String> idiomas, List<String> intereses) {
		
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.nacionalidades = nacionalidades;
		this.idiomas = idiomas;
		this.intereses = intereses;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	
	public List<String> getNacionalidades() {
		return nacionalidades;
	}

	public List<String> getIdiomas() {
		return idiomas;
	}

	public List<String> getIntereses() {
		return intereses;
	}
}
