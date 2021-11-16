package com.tpo.bankjob.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;
import com.tpo.bankjob.model.dao.PostulanteDao;
import com.tpo.bankjob.model.utils.View;

@Component
@Entity
@Table(name = "postulante")
@JsonRootName(value = "postulante")
@JsonView(View.Public.class)
public class Postulante implements UserDetails {
	
	private static final long serialVersionUID = 1125979622368767166L;
	
	@Autowired
	@Transient
	private PostulanteDao postulanteDao;

	@JsonView(View.Public.class)
	@JsonProperty("id")
	@Column(name = "id")
	private @Id String id;
	
	@JsonView(View.Internal.class)
	@JsonProperty("username")
	@Column(name = "username")
	private String username;
	
	@JsonView(View.Internal.class)
	@JsonProperty("password")
	@Column(name = "password")
	private String password;
	
	@JsonView(View.Public.class)
	@JsonProperty("nombre")
	@Column(name = "nombre")
	private String nombre;
	
	@JsonView(View.Public.class)
	@JsonProperty("apellido")
	@Column(name = "apellido")
	private String apellido;
	
	@JsonView(View.Public.class)
	@Column(name = "canal_notificacion")
	@JsonProperty("canal_notificacion")
	private CanalNotificacion canalNotificacion;
	
	@JsonView(View.Public.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ", timezone="America/Buenos Aires")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@JsonProperty("fecha_nacimiento")
	@Column(name = "fecha_nacimiento")
	private DateTime fechaNacimiento;
	    
	@JsonView(View.Public.class)
	@JsonProperty("skills")
	@Column(name = "skills")	
    @OneToMany(mappedBy = "ownerId")
	@LazyCollection(LazyCollectionOption.FALSE)
    private List<Skill> skills;
	
	@JsonView(View.Public.class)
	@JsonProperty("intereses")
	@Column(name = "intereses")	
    @OneToMany(mappedBy = "idPostulante")
	@LazyCollection(LazyCollectionOption.FALSE)
    private List<Interes> intereses;
	
	@JsonView(View.Public.class)
	@JsonProperty("postulaciones")
    @OneToMany(mappedBy = "postulante")
    private List<Postulacion> postulaciones;
	
	public Postulante() {
		this.postulaciones = new ArrayList<>();
		this.skills = new ArrayList<>();
		this.intereses = new ArrayList<>();
	}
	
	public Postulante(String id, String username,
			String password, String nombre, String apellido, 
			DateTime fechaNacimiento) {
		this();
		this.id = id;
		this.username = username;
		this.password = password;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public DateTime getFechaNacimiento() {
		return fechaNacimiento;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void setFechaNacimiento(DateTime fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
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
	
	public CanalNotificacion getCanalNotificacion() {
		return canalNotificacion;
	}

	public void setCanalNotificacion(CanalNotificacion canalNotificacion) {
		this.canalNotificacion = canalNotificacion;
	}

	public void setIntereses(List<Interes> intereses) {
		this.intereses = intereses;
	}
	
	@JsonView(View.Internal.class)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<>();
	}

	@JsonView(View.Internal.class)
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@JsonView(View.Internal.class)
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@JsonView(View.Internal.class)
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@JsonView(View.Internal.class)
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public List<Interes> getIntereses() {
		return this.intereses;
	}

	@JsonView(View.Internal.class)
	public void notificarNovedadesIntereses(List<Publicacion> novedadesIntereses) {
		// TODO #ADOO STRATEGY + ADAPTER
		
	}
	
	public String register(Postulante postulanteVO) {
		return postulanteDao.register(postulanteVO);
	}

	public List<Postulante> findAll() {
		return postulanteDao.findAll();
	}

	public Optional<Postulante> findById(String id) {
		return postulanteDao.findById(id);
	}

	@JsonView(View.Internal.class)
	public Interes addInteres(Interes interes) {
		return postulanteDao.addInteres(interes);
	}
}
