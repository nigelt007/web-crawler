package com.scout24.webcrawler.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.scout24.webcrawler.service.WebCrawlerService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WebCrawlerControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	private WebCrawlerService webCrawlerService;

	private final String TEST_GOOGLE_URI = "https://www.google.com";

	@Test
	public void testApi() throws Exception {

		// when(webCrawlerService.analyseUrl(any(String.class))).
		this.mockMvc.perform(
				get("/api/webCrawler/v1/analyse").param("uri", TEST_GOOGLE_URI).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

	}

}
