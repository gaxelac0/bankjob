package com.tpo.bankjob;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tpo.bankjob.model.Empresa;
import com.tpo.bankjob.model.Publicacion;

@SpringBootTest
class BankjobApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void givenEmpresaVoJsonWhenSerializeThenSerializeSucessfully() throws IOException {

		// given // TODO: corregir jsons
		String SOURCE_JSON = "{\"id\":\"1\",\"razon_social\":\"pepe\","
				+ "\"publicaciones\":[]}";

		// when
		Empresa empresaVO = new ObjectMapper()
				.readerFor(Empresa.class)
				.readValue(SOURCE_JSON);

		// then
		Assert.notNull(empresaVO, "En caso de ser null, no pudo deserializarse.");
	}
	
	@Test
	public void givenPublicacionVoJsonWhenSerializeThenSerializeSucessfully() throws IOException {

		// given
		String SOURCE_JSON = "{\"id\":\"1\",\"id_empresa\":\"1\",\"titulo\":\"pepe\","
				+ "\"descripcion\":\"pepe\",\"modalidad\":\"1\",\"tipo_trabajo\":\"1\","
				+ "\"locacion\":\"pepe\",\"categoria\":\"pepe\",\"sueldo_ofrecido\":\"100\"}";

		// when
		Publicacion publicacionVO = new ObjectMapper()
				.readerFor(Publicacion.class)
				.readValue(SOURCE_JSON);

		// then
		Assert.notNull(publicacionVO, "En caso de ser null, no pudo deserializarse.");
	}
	
	
//	@Test
//	public void givenPublicationWrapperJsonWhereSerializeThenSerializeSucessfully() throws IOException {
//
//		// given
//		String SOURCE_JSON = "{\"empresa\":{\"id\":\"1\",\"razon_social\":\"pepe\",\"publicaciones\":[]},\"publicacion\":{\"id\":\"1\",\"titulo\":\"pepe\","
//				+ "\"descripcion\":\"pepe\",\"modalidad\":\"1\",\"tipo_trabajo\":\"1\","
//				+ "\"locacion\":\"pepe\",\"categoria\":\"pepe\",\"sueldo_ofrecido\":\"100\"}}";
//
//		// when
//		PublicationEmpresaWrapper wrapper = new ObjectMapper()
//				.readerFor(PublicationEmpresaWrapper.class)
//				.readValue(SOURCE_JSON);
//
//		// then
//		Assert.notNull(wrapper, "En caso de ser null, no pudo deserializarse.");
//	}
	
	@Test
	public void givenEmpresaVoPojoWhenDeserializeThenDeserializeSucessfully() throws IOException {

		// given
		Empresa empresaVO = new Empresa("", "", ""); // TODO: corregir constructor

		// when
		String deserialized = new ObjectMapper().writeValueAsString(empresaVO);

		// then
		Assert.notNull(deserialized, "En caso de ser null, no pudo deserializarse.");
	}
	
	@Test
	public void givenPublicacionPojoWhenDeserializeThenDeserializeSucessfully() throws IOException {

		// given
		Publicacion publicacionVO = new Publicacion(); // TODO fix test

		// when
		String deserialized = new ObjectMapper().writeValueAsString(publicacionVO);

		// then
		Assert.notNull(deserialized, "En caso de ser null, no pudo deserializarse.");
	}
	
	
//	@Test
//	public void givenPublicationEmpresaWrapperPojoWhereDeserializeThenDeserializeSucessfully() throws IOException {
//
//		// given
//	
//
//		// when
//
//
//		// then
////		Assert.notNull(wrapper, "En caso de ser null, no pudo deserializarse.");
//	}
}
