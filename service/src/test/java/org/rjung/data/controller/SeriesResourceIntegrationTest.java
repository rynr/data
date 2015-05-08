package org.rjung.data.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rjung.data.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DataService.class)
@WebAppConfiguration
public class SeriesResourceIntegrationTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void expectGettingSeriesIndexToSucceed() throws Exception {
		this.mockMvc.perform(get("/series")).andExpect(status().isOk());
	}

	@Test
	public void expectPostingSeriesSucceeds() throws Exception {
		this.mockMvc.perform(
				post("/series")
						.content("{\"name\":\"" + randomString() + "\"}")
						.contentType(MediaType.APPLICATION_JSON)).andExpect(
				status().isOk());
	}

	@Test
	public void expectPostingDuplicateSeriesFails() throws Exception {
		String name = randomString();
		this.mockMvc.perform(
				post("/series").content("{\"name\":\"" + name + "\"}")
						.contentType(MediaType.APPLICATION_JSON)).andExpect(
				status().isOk());
		this.mockMvc.perform(
				post("/series").content("{\"name\":\"" + name + "\"}")
						.contentType(MediaType.APPLICATION_JSON)).andExpect(
				status().isUnprocessableEntity());
	}

	private String randomString() {
		return UUID.randomUUID().toString();
	}
}
