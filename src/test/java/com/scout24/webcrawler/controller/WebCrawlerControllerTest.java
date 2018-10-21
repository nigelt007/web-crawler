package com.scout24.webcrawler.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;

import com.scout24.webcrawler.WebCrawlerApplicationTests;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WebCrawlerControllerTest extends WebCrawlerApplicationTests {

	private static final String TEST_GOOGLE_URI = "https://www.google.com";

	private static final String INVALID_GOOGLE_URI = "www.google.com";

	@Test
	public void testApi() throws Exception {

		mockMvc.perform(
				get("/api/webCrawler/v1/analyse").param("uri", TEST_GOOGLE_URI).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

	}

	@Test
	public void testApi_ForInvalidUrl() throws Exception {

		MvcResult mvcresult = mockMvc.perform(
				get("/api/webCrawler/v1/analyse").param("uri", INVALID_GOOGLE_URI).accept(MediaType.APPLICATION_JSON))
				.andReturn();
		assertEquals(HttpStatus.BAD_REQUEST.value(), mvcresult.getResponse().getStatus());

	}

}
