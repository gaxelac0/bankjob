package com.tpo.bankjob.model.builder;

import java.util.List;

import org.joda.time.DateTime;

import com.tpo.bankjob.model.Empresa;
import com.tpo.bankjob.model.Modalidad;
import com.tpo.bankjob.model.Publicacion;
import com.tpo.bankjob.model.Skill;
import com.tpo.bankjob.model.Tarea;
import com.tpo.bankjob.model.TipoTrabajo;
import com.tpo.bankjob.model.vo.PublicacionVO;

public class PublicacionVOBuilder {
	
	private PublicacionVO vo;
	
    public PublicacionVOBuilder() {
        vo = new PublicacionVO();
    }
    
    public PublicacionVOBuilder(Publicacion publicacion) {
    	this();
    	setId(publicacion.getId());
    	setTitulo(publicacion.getTitulo());
    	setDescripcion(publicacion.getDescripcion());
    	setModalidad(publicacion.getModalidad());
    	setTipoTrabajo(publicacion.getTipoTrabajo());
    	setLocacion(publicacion.getLocacion());
    	setCategoria(publicacion.getCategoria());
    	setSueldoOfrecido(publicacion.getSueldoOfrecido());
    	setEmpresa(publicacion.getEmpresa());
    	setFechaVigencia(publicacion.getFechaVigencia());
    	setSkills(publicacion.getSkills());
    	setTareas(publicacion.getTareas());
		setTitulo(publicacion.getTitulo());
		setImage(publicacion.getImg());
		setOpen(publicacion.isOpen());
    }
    
    private PublicacionVOBuilder setId(String id) {
    	vo.setId(id);
    	return this;
	}

    private PublicacionVOBuilder setOpen(boolean open) {
		vo.setOpen(open);
		return this;
	}

	public PublicacionVOBuilder setImage(String img) {
		vo.setImg(img);
		return this;
	}

	public PublicacionVOBuilder setTareas(List<Tarea> tareas) {
    	vo.setTareas(tareas);
    	return this;
	}

	public PublicacionVOBuilder setSkills(List<Skill> skills) {
		vo.setSkills(skills);
		return this;
	}

	public PublicacionVOBuilder setFechaVigencia(DateTime fechaVigencia) {
		vo.setFechaVigencia(fechaVigencia);
		return this;
	}

	public PublicacionVOBuilder setEmpresa(Empresa empresa) {
		vo.setEmpresa(empresa);
		return this;
	}

	public PublicacionVOBuilder setTitulo(String titulo) {
		vo.setTitulo(titulo);
		return this;
    }
    
    public PublicacionVOBuilder setDescripcion(String categoria) {
    	vo.setDescripcion(categoria);
    	return this;
    }

    public PublicacionVOBuilder setModalidad(Modalidad modalidad) {
    	vo.setModalidad(modalidad);
    	return this;
    }

    public PublicacionVOBuilder setTipoTrabajo(TipoTrabajo tipoTrabajo) {
    	vo.setTipoTrabajo(tipoTrabajo);
    	return this;
    }

    public PublicacionVOBuilder setLocacion(String locacion) {
    	vo.setLocacion(locacion);
    	return this;
    }

    public PublicacionVOBuilder setCategoria(String categoria) {
    	vo.setCategoria(categoria);
    	return this;
    }
    
    public PublicacionVOBuilder setSueldoOfrecido(double sueldoOfrecido) {
    	vo.setSueldoOfrecido(sueldoOfrecido);
    	return this;
    }
    
    public PublicacionVO build() {
    	return this.vo;
    }
}
