package com.tpo.bankjob.controller;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.base.Supplier;
import com.tpo.bankjob.model.Postulacion;
import com.tpo.bankjob.model.Postulante;
import com.tpo.bankjob.model.Publicacion;
import com.tpo.bankjob.model.exception.BadFormatException;
import com.tpo.bankjob.model.exception.InvalidActionException;
import com.tpo.bankjob.model.utils.View;
import com.tpo.bankjob.model.vo.Modalidad;
import com.tpo.bankjob.model.vo.TipoTrabajo;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/reporte")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class ReporteController {
	
	@Autowired
	PostulanteController postulanteController;
	
	@Autowired
	PostulacionController postulacionController;
		
	@Autowired
	PublicacionController publicacionController;
	
	@Scheduled(fixedRate = 180000)
	public void generarInformeFavs() {
		
		for(Postulante p: postulanteController.getPostulantes()
							.stream()
							.filter(p -> !p.getIntereses().isEmpty())
							.collect(Collectors.toList())) {
			
			List<Publicacion> result = new ArrayList<>();
			p.getIntereses()
			.stream()
			.map(i -> i.getCategoria())
			.forEach(c -> {
				List<Publicacion> l = publicacionController.getPublicacionesByCategoria(c);
				if(!l.isEmpty()) result.add(l.get(0));
			});
			
			if(!result.isEmpty())
				p.notificarNovedadesIntereses(result);
		}
	}
	
	/////////////////////////////////////// -> REPORTES
	@JsonView(View.Internal.class)
	@GetMapping("/01/{periodo}")
	@ResponseBody
	public Publicacion obtenerPublicacionMasSolicitada(
			@PathVariable String periodo) {
		
		YearMonth ym = parseAndGetYearMonth(periodo);

		DateTime min = new DateTime(ym.getYear(), ym.getMonthValue(),
				1, 0, 0);
		DateTime max = new DateTime(ym.getYear(), ym.getMonthValue(),
				min.dayOfMonth().getMaximumValue(), 0, 0);
	
		HashMap<Publicacion, Long> map = new HashMap<>();
			
		List<Postulacion> listaPeriodo = postulacionController.getPostulaciones().stream()		
		.filter((p) -> p.getFechaPostulacion().isAfter(min))
		.filter((p) -> p.getFechaPostulacion().isBefore(max)).collect(Collectors.toList());
		
		if(listaPeriodo.isEmpty())
			throw new InvalidActionException("No hay postulaciones para el periodo");
		
		for(Postulacion postulacion: listaPeriodo) {
			Publicacion p = postulacion.getPublicacion();
			if(!map.containsKey(p)) map.put(p, 1L);
			else map.put(p, map.get(p)+1L);
		}
		
		Stream<Map.Entry<Publicacion,Long>> sorted = map.entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
		
		return sorted.findFirst().get().getKey();
	}
	
	@GetMapping("/02/{cantidad}")
	public @ResponseBody List<String> obtenerCategoriasMasSeleccionadas(
			@PathVariable int cantidad) {
		
		HashMap<String, Long> map = new HashMap<>();
		for(Publicacion pub: publicacionController.getPublicaciones()) {
			String catego = pub.getCategoria();
			if(!map.containsKey(catego)) map.put(catego, 1L);
			else map.put(catego, map.get(catego)+1L);
		}
		
		Stream<Map.Entry<String,Long>> sorted = map.entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.limit(cantidad);
		
		List<String> result = new ArrayList<>();
		sorted.collect(Collectors.toList()).forEach((p) -> result.add(p.getKey()));
		return result;
	}
	
	@JsonView(View.Internal.class)
	@GetMapping("/03")
	public @ResponseBody Publicacion obtenerPublicacionMasAccesible() {
		
		List<Publicacion> list = publicacionController.getPublicaciones().stream()
		.filter((p) -> p.getTipoTrabajo().equals(TipoTrabajo.REMOTO))
		.filter((p) -> p.getModalidad().equals(Modalidad.PART_TIME))
		.collect(Collectors.toList());
		
		if(list.isEmpty())
			throw new InvalidActionException("No hay publicaciones en el sistema.");
		
		list.sort(Comparator.comparing(p -> 
					((Publicacion)p).getTareas().size()
					+ ((Publicacion)p).getSkills()
					.stream()
					.filter(s -> s.isMandatory())
					.collect(Collectors.toList()).size()
				)
		);
		
		return list.get(0);
	}
	
	@JsonView(View.Internal.class)
	@GetMapping("/04")
	public @ResponseBody Publicacion obtenerPublicacionMasExigente() {
		List<Publicacion> list = publicacionController.getPublicaciones();
		if(list.isEmpty())
			throw new InvalidActionException("No hay publicaciones en el sistema.");
		list.sort(Comparator.comparing(p -> ((Publicacion)p).getSkills().size()).reversed());
		return list.get(0);
	}
	
	
	private YearMonth parseAndGetYearMonth(String periodo) {

		if(StringUtils.isBlank(periodo)
				|| periodo.length() != 6
				|| !assertException(() -> Integer.parseInt(periodo))) 
			throw new BadFormatException();
		
		String mmYYYY = periodo.substring(0,2)+"/"+periodo.substring(2,6);
		return YearMonth.parse(mmYYYY, DateTimeFormatter.ofPattern("MM/uuuu"));
	}

	private static <T> boolean assertException(Supplier<? extends T> supplier) {
	    try {
	    	supplier.get();
	        return true;
	    } catch (Exception e) {
	        return false;
	    }
	}
}
