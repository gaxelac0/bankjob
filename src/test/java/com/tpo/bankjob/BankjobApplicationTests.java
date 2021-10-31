package com.tpo.bankjob;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tpo.bankjob.model.utils.PublicationEmpresaWrapper;
import com.tpo.bankjob.model.vo.EmpresaVO;
import com.tpo.bankjob.model.vo.PublicacionVO;

@SpringBootTest
class BankjobApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void givenEmpresaVoJsonWhenSerializeThenSerializeSucessfully() throws IOException {

		// given
		String SOURCE_JSON = "{\"id\":\"1\",\"razon_social\":\"pepe\","
				+ "\"publicaciones\":[]}";

		// when
		EmpresaVO empresaVO = new ObjectMapper()
				.readerFor(EmpresaVO.class)
				.readValue(SOURCE_JSON);

		// then
		Assert.notNull(empresaVO, "En caso de ser null, no pudo deserializarse.");
	}
	
	@Test
	public void givenPublicacionVoJsonWhenSerializeThenSerializeSucessfully() throws IOException {

		// given
		String SOURCE_JSON = "{\"id\":\"1\",\"titulo\":\"pepe\","
				+ "\"descripcion\":\"pepe\",\"modalidad\":\"1\",\"tipo_trabajo\":\"1\","
				+ "\"lugar\":\"pepe\",\"categoria\":\"pepe\",\"sueldo_ofrecido\":\"100\"}";

		// when
		PublicacionVO publicacionVO = new ObjectMapper()
				.readerFor(PublicacionVO.class)
				.readValue(SOURCE_JSON);

		// then
		Assert.notNull(publicacionVO, "En caso de ser null, no pudo deserializarse.");
	}
	
	
	@Test
	public void givenPublicationWrapperJsonWhereSerializeThenSerializeSucessfully() throws IOException {

		// given
		String SOURCE_JSON = "{\"empresa\":{\"id\":\"1\",\"razon_social\":\"pepe\",\"publicaciones\":[]},\"publicacion\":{\"id\":\"1\",\"titulo\":\"pepe\","
				+ "\"descripcion\":\"pepe\",\"modalidad\":\"1\",\"tipo_trabajo\":\"1\","
				+ "\"lugar\":\"pepe\",\"categoria\":\"pepe\",\"sueldo_ofrecido\":\"100\"}}";

		// when
		PublicationEmpresaWrapper wrapper = new ObjectMapper()
				.readerFor(PublicationEmpresaWrapper.class)
				.readValue(SOURCE_JSON);

		// then
		Assert.notNull(wrapper, "En caso de ser null, no pudo deserializarse.");
	}
	
	@Test
	public void givenEmpresaVoPojoWhenDeserializeThenDeserializeSucessfully() throws IOException {

		// given
		EmpresaVO empresaVO = new EmpresaVO();

		// when
		String deserialized = new ObjectMapper().writeValueAsString(empresaVO);

		// then
		Assert.notNull(deserialized, "En caso de ser null, no pudo deserializarse.");
	}
	
	@Test
	public void givenPublicacionPojonWhenDeserializeThenDeserializeSucessfully() throws IOException {

		// given
		PublicacionVO publicacionVO = new PublicacionVO();

		// when
		String deserialized = new ObjectMapper().writeValueAsString(publicacionVO);

		// then
		Assert.notNull(deserialized, "En caso de ser null, no pudo deserializarse.");
	}
	
	
	@Test
	public void givenPublicationEmpresaWrapperPojoWhereDeserializeThenDeserializeSucessfully() throws IOException {

		// given
	

		// when


		// then
//		Assert.notNull(wrapper, "En caso de ser null, no pudo deserializarse.");
	}
}
