package com.scout24.webcrawler.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.scout24.webcrawler.WebCrawlerApplicationTests;
import com.scout24.webcrawler.dto.AnalysisDTO;
import com.scout24.webcrawler.dto.LinkDTO;
import com.scout24.webcrawler.dto.LinkDTO.LinkDTOBuilder;
import com.scout24.webcrawler.service.WebCrawlerService;

public class WebCrawlerControllerTest extends WebCrawlerApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	private static final String TEST_GOOGLE_URI = "https://www.google.com";

	private static final String INVALID_GOOGLE_URI = "www.google.com";

	private static final String TEST_SIMPLE_PAGE = "https://www.york.ac.uk/teaching/cws/wws/webpage1.html";

	@MockBean
	private WebCrawlerService webCrawlerService;

	private static final String TEST_JSON = "{\r\n"
			+ "  \"url\": \"https://www.york.ac.uk/teaching/cws/wws/webpage1.html\",\r\n" + "  \"numLinks\": 2,\r\n"
			+ "  \"status\": 200,\r\n" + "  \"available\": true,\r\n" + "  \"internal\": [\r\n" + "    {\r\n"
			+ "      \"url\": \"https://www.york.ac.uk/teaching/cws/wws/webpage2.html\",\r\n"
			+ "      \"redirectedStatus\": 0,\r\n"
			+ "      \"redirectedUrl\": \"https://www.york.ac.uk/teaching/cws/wws/webpage2.html\",\r\n"
			+ "      \"available\": true,\r\n" + "      \"error\": null\r\n" + "    },\r\n" + "    {\r\n"
			+ "      \"url\": \"https://www.york.ac.uk/teaching/cws/wws/col3.html\",\r\n"
			+ "      \"redirectedStatus\": 0,\r\n"
			+ "      \"redirectedUrl\": \"https://www.york.ac.uk/teaching/cws/wws/col3.html\",\r\n"
			+ "      \"available\": true,\r\n" + "      \"error\": null\r\n" + "    }\r\n" + "  ],\r\n"
			+ "  \"external\": []\r\n" + "}";

	private AnalysisDTO mockAnalysisDto = new AnalysisDTO();

	@Before
	public void setup() {
		AnalysisDTO.AnalysisDTOBuilder analysisDtoBuilder = AnalysisDTO.newBuilder()
				.setUrl("https://www.york.ac.uk/teaching/cws/wws/webpage1.html").setNumberOfLinks(2).setStatus(200)
				.setAvailable(true);
		LinkDTOBuilder linkDtoBuilder = null;
		linkDtoBuilder = LinkDTO.newBuilder().setUrl("https://www.york.ac.uk/teaching/cws/wws/col3.html")
				.setRedirectedStatus(0).setRedirectedUrl("https://www.york.ac.uk/teaching/cws/wws/col3.html")
				.setAvailable(true).setError(null);
		LinkDTO link1 = linkDtoBuilder.makeLinkDTO();
		linkDtoBuilder = LinkDTO.newBuilder().setUrl("https://www.york.ac.uk/teaching/cws/wws/webpage2.html")
				.setRedirectedStatus(0).setRedirectedUrl("https://www.york.ac.uk/teaching/cws/wws/webpage2.html")
				.setAvailable(true).setError(null);
		LinkDTO link2 = linkDtoBuilder.makeLinkDTO();
		List<LinkDTO> internal = new ArrayList<>();
		internal.add(link1);
		internal.add(link2);
		analysisDtoBuilder.setInternal(internal);
		mockAnalysisDto = analysisDtoBuilder.makeCandidateApplicationDTO();
	}

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

	@Test
	public void testApi_ForSimpleValidUrl() throws Exception {

		Mockito.when(webCrawlerService.analyseInputUrl(Mockito.anyString())).thenReturn(mockAnalysisDto);

		MvcResult mvcresult = mockMvc.perform(
				get("/api/webCrawler/v1/analyse").param("uri", TEST_SIMPLE_PAGE).accept(MediaType.APPLICATION_JSON))
				.andReturn();
		mvcresult.getResponse().getRedirectedUrl();
		JSONAssert.assertEquals(TEST_JSON, mvcresult.getResponse().getContentAsString(), false);

	}

}
