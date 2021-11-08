package com.tpo.bankjob.model.dao;

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
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tpo.bankjob.model.exception.EmpresaNotFoundException;
import com.tpo.bankjob.model.exception.InvalidActionException;
import com.tpo.bankjob.model.repository.EmpresaRepository;
import com.tpo.bankjob.model.repository.PublicacionRepository;
import com.tpo.bankjob.model.repository.SkillRepository;
import com.tpo.bankjob.model.repository.TareaRepository;
import com.tpo.bankjob.model.state.EstadoPublicacionAbierto;
import com.tpo.bankjob.model.vo.EmpresaVO;
import com.tpo.bankjob.model.vo.PublicacionVO;
import com.tpo.bankjob.security.RequestTokenService;

@Component
public class PublicacionDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PublicacionDao.class);
	private static final String IMG_PATH = "src/main/resources/static/img/";
	
	@Autowired
	PublicacionRepository publicacionRepository;
	
	@Autowired
	EmpresaRepository empresaRepository;
	
	@Autowired
	SkillRepository skillRepository;
	
	@Autowired
	TareaRepository tareaRepository;
	
	public PublicacionVO add(PublicacionVO publicacionVO) {
		
		Optional<EmpresaVO> opt = empresaRepository.findById(RequestTokenService.getRequestToken());
		if(!opt.isPresent()) {
			throw new EmpresaNotFoundException(RequestTokenService.getRequestToken());
		}
		
		EmpresaVO empresaVO = opt.get();
		
		// agrega la publicacion al repo de publicaciones
		publicacionVO.setEmpresa(empresaVO);
		publicacionRepository.save(publicacionVO);
		publicacionVO.getSkills().stream().forEach((rs) ->
			rs.setOwnerId(publicacionVO.getId().toString())
		);
		
		publicacionVO.getTareas().stream().forEach((t) ->
			t.setPublicacion(publicacionVO)
		);
		
		skillRepository.saveAllAndFlush(publicacionVO.getSkills());
		tareaRepository.saveAllAndFlush(publicacionVO.getTareas());
		
		generarTitulo(publicacionVO);
		generarImagen(publicacionVO);
		
		publicacionRepository.saveAndFlush(publicacionVO);
		
		// actualizar el obj empresa y el repo
		empresaVO.addPublicacion(publicacionVO);
		empresaRepository.saveAndFlush(empresaVO);
		
		return publicacionVO;
	}

	public Optional<PublicacionVO> get(String id) {
		return publicacionRepository.findById(id);
	}

	public List<PublicacionVO> findAll() {
		return publicacionRepository.findAll();
	}

	public PublicacionVO open(PublicacionVO publicacionVO) {
		
		Optional<EmpresaVO> opt = empresaRepository.findById(RequestTokenService.getRequestToken());
		if(!opt.isPresent()) {
			throw new EmpresaNotFoundException(
					RequestTokenService.getRequestToken());
		}
		
		EmpresaVO empresaVO = opt.get();
		if(!publicacionVO.getEmpresa().getId().equalsIgnoreCase(empresaVO.getId())) {
			throw new InvalidActionException("Solo la propia empresa puede"
					+ " abrir sus publicaciones cerradas.");
		}
		
		if(!publicacionVO.isClosed()) {
			throw new InvalidActionException("No se pueden abrir publicaciones "
					+ "que no se encuentren en estado cerrado.");
		}
		
		publicacionVO.setFechaVigencia(Instant.now().toDateTime().plusWeeks(2));
		publicacionVO.setEstado(new EstadoPublicacionAbierto(publicacionVO));
		publicacionRepository.saveAndFlush(publicacionVO);
		
		LOGGER.info("Publicacion ID(" + publicacionVO.getId() + ") abierta manualmente por la empresa.");
		return publicacionVO;
	}
	
	private void generarTitulo(PublicacionVO publicacionVO) {
		if(StringUtils.isBlank(publicacionVO.getTitulo())) {
			publicacionVO.setTitulo(publicacionVO.getLugar() + " | "
					+ publicacionVO.getCategoria() + " | "
					+ publicacionVO.getTipoTrabajo()  + " | "
					+ (!publicacionVO.getSkills().isEmpty() 
							? publicacionVO.getSkills().get(0).getName().concat(" ") 
									: "Trabajo ")
					+ publicacionVO.getSueldoOfrecido() + "$");
		}
	}

	private void generarImagen(PublicacionVO publicacionVO) {
		
		BufferedImage image = null;
		try {
			File file = new File(IMG_PATH+getImgNameByLugar(publicacionVO.getLugar()));
			file.getAbsolutePath();
			image = ImageIO.read(file);
		} catch (IOException e) {
			throw new InvalidActionException("No se pudo generar la imagen. Detalle: " + e.getMessage());
		}
		
		Font font = new Font("Arial", Font.BOLD, 18);
		AttributedString attributedText = new AttributedString(publicacionVO.getTitulo());
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
		
		publicacionVO.setImg(DatatypeConverter.printBase64Binary(output.toByteArray()));
	}

	private String getImgNameByLugar(String lugar) {
		
		String ret = "def.jpg";
		switch(lugar) {
			case "Buenos Aires": ret = "ba.jpg"; break;
			case "Cordoba": ret = "cordoba.jpg"; break;
			case "Montevideo": ret = "montevideo.jpg"; break;
			default: ret = "def.jpg"; break;
		}
		return ret;
	}
	
    public void transicionarPublicaciones() {
    	
		// las publicaciones finalizadas no necesitan ser transicionadas
		publicacionRepository.findAll().stream()
		.filter((p) -> p.isClosed() || p.isOpen())
		.forEach((p) -> publicacionRepository.saveAndFlush(p.transicionar()));
    }

}
