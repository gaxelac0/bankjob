package com.tpo.bankjob.model.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.tpo.bankjob.model.observer.IObservable;
import com.tpo.bankjob.model.observer.IObserver;

@Entity
@Table(name = "empresa")
@JsonRootName(value = "empresa")
public class EmpresaVO implements UserDetails, IObserver {

	private static final long serialVersionUID = 4384739614806100984L;

	@JsonProperty("id")
	@Column(name = "id")
	private @Id String id;
	
	@JsonProperty("razon_social")
	@Column(name = "razon_social")
	private String razonSocial;
	
	@JsonProperty("publicaciones")
	@Column(name = "publicaciones")
	@OneToMany(mappedBy = "empresa",fetch = FetchType.EAGER)
	private List<PublicacionVO> publicaciones;
	
	@JsonProperty("username")
	@Column(name = "username")
	private String username;
	
	@JsonProperty("password")
	@Column(name = "password")
	private String password;
	
	@Column(name = "canal_notificacion")
	@JsonProperty("canal_notificacion")
	private CanalNotificacionEnum canalNotificacion;
	
	public EmpresaVO() {
		this.publicaciones = new ArrayList<>();
	}

	public EmpresaVO(String id, String username, String password) {
		this();
		this.id = id;
		this.razonSocial = "";
		this.username = username;
		this.password = password;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
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

	public CanalNotificacionEnum getCanalNotificacion() {
		return canalNotificacion;
	}

	public void setCanalNotificacion(CanalNotificacionEnum canalNotificacion) {
		this.canalNotificacion = canalNotificacion;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<>();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void notificarPostulacion(IObservable observable) {
		
		
		
		// (#ADOO) STRATEGY & ADAPTER)
		System.out.println();
	}
}
