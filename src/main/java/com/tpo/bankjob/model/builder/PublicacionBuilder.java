package com.tpo.bankjob.model.builder;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.AttributedString;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import com.tpo.bankjob.model.Empresa;
import com.tpo.bankjob.model.Modalidad;
import com.tpo.bankjob.model.Publicacion;
import com.tpo.bankjob.model.Skill;
import com.tpo.bankjob.model.Tarea;
import com.tpo.bankjob.model.TipoTrabajo;
import com.tpo.bankjob.model.exception.InvalidActionException;
import com.tpo.bankjob.model.vo.PublicacionVO;

public class PublicacionBuilder {
	
	private static final String IMG_PATH = "src/main/resources/static/img/";
	
	private Publicacion publicacion;
	
    public PublicacionBuilder() {
        publicacion = new Publicacion();
    }
    
    public PublicacionBuilder(PublicacionVO vo) {
    	this();
    	setId(vo.getId());
    	setTitulo(vo.getTitulo());
    	setDescripcion(vo.getDescripcion());
    	setModalidad(vo.getModalidad());
    	setTipoTrabajo(vo.getTipoTrabajo());
    	setLocacion(vo.getLocacion());
    	setCategoria(vo.getCategoria());
    	setSueldoOfrecido(vo.getSueldoOfrecido());
    	setEmpresa(vo.getEmpresa());
    	setFechaVigencia(vo.getFechaVigencia());
    	setSkills(vo.getSkills());
    	setTareas(vo.getTareas());
		setOrGenerateTitulo();
		setOrGenerateImage();
    }
    
    private void setId(String id) {
		publicacion.setId(id);		
	}

	private void setTareas(List<Tarea> tareas) {
		publicacion.setTareas(tareas);
	}

	private void setSkills(List<Skill> skills) {
		publicacion.setSkills(skills);
	}

	private void setFechaVigencia(DateTime fechaVigencia) {
		publicacion.setFechaVigencia(fechaVigencia);		
	}

	private void setEmpresa(Empresa empresa) {
		publicacion.setEmpresa(empresa);
		
	}

	public void setTitulo(String titulo) {
    	 publicacion.setTitulo(titulo);
    }
    
    public void setDescripcion(String categoria) {
    	 publicacion.setDescripcion(categoria);
    }

    public void setModalidad(Modalidad modalidad) {
    	publicacion.setModalidad(modalidad);
    }

    public void setTipoTrabajo(TipoTrabajo tipoTrabajo) {
    	publicacion.setTipoTrabajo(tipoTrabajo);
    }

    public void setLocacion(String locacion) {
    	publicacion.setLocacion(locacion);
    }

    public void setCategoria(String categoria) {
    	publicacion.setCategoria(categoria);
    }
    
    public void setSueldoOfrecido(double sueldoOfrecido) {
    	publicacion.setSueldoOfrecido(sueldoOfrecido);
    }
    
	private void setOrGenerateTitulo() {
		if(StringUtils.isBlank(publicacion.getTitulo())) {
			publicacion.setTitulo(publicacion.getLocacion() + " | "
					+ publicacion.getCategoria() + " | "
					+ publicacion.getTipoTrabajo()  + " | "
					+ (!publicacion.getSkills().isEmpty() 
							? publicacion.getSkills().get(0).getName().concat(" ") 
									: "Trabajo ")
					+ publicacion.getSueldoOfrecido() + "$");
		}
	}

	private void setOrGenerateImage() {
		
		if(StringUtils.isBlank(publicacion.getImg())) {
			BufferedImage image = null;
			try {
				File file = new File(IMG_PATH+getImgNameByLocacion(publicacion.getLocacion()));
				file.getAbsolutePath();
				image = ImageIO.read(file);
			} catch (IOException e) {
				throw new InvalidActionException("No se pudo generar la imagen. Detalle: " + e.getMessage());
			}
			
			Font font = new Font("Arial", Font.BOLD, 18);
			AttributedString attributedText = new AttributedString(publicacion.getTitulo());
			attributedText.addAttribute(TextAttribute.FONT, font);
			attributedText.addAttribute(TextAttribute.FOREGROUND, Color.GREEN);
			
			Graphics g = image.getGraphics();
			g.drawString(attributedText.getIterator(), 0, 20);
			
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			try {
				ImageIO.write(image, "jpg", output);
			} catch (IOException e) {
				throw new InvalidActionException("No se pudo generar la imagen. Detalle: " + e.getMessage());
			};
			
			publicacion.setImg(DatatypeConverter.printBase64Binary(output.toByteArray()));
		}
	}

	private String getImgNameByLocacion(String lugar) {
		
		String ret = "def.jpg";
		switch(lugar) {
			case "Buenos Aires": ret = "ba.jpg"; break;
			case "Cordoba": ret = "cordoba.jpg"; break;
			case "Montevideo": ret = "montevideo.jpg"; break;
			default: ret = "def.jpg"; break;
		}
		return ret;
	}
    
    public Publicacion build() {
    	return this.publicacion;
    }
}
