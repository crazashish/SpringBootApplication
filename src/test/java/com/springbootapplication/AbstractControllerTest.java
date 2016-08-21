package com.springbootapplication;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootapplication.controller.BaseController;

@WebAppConfiguration
public abstract class AbstractControllerTest extends AbstractTest {

	protected MockMvc mockMvc;
	@Autowired
	protected WebApplicationContext webApplicationContext;

	protected void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.build();
	}

	protected void setup(BaseController controller) {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	protected <T> T mapFromJson(String json, Class<T> classe)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, classe);
	}

	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(obj);
	}

}
