package com.scout24.webcrawler.controller;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.junit.Before;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import com.scout24.webcrawler.WebCrawlerApplicationTests;
import com.scout24.webcrawler.dto.AnalysisDTO;

public class WebCrawlerControllerIntegrationTest extends WebCrawlerApplicationTests {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	private static final String TEST_JSON_ORG_URL = "https://www.json.org";

	@Before
	public void before() {
		// headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		// headers.setContentType(MediaType.APPLICATION_JSON);
	}

	
	public void testService_JsonOrgUrl() throws JSONException {

		// HttpEntity<AnalysisDTO> entity = new HttpEntity<AnalysisDTO>(null, headers);
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("url", TEST_JSON_ORG_URL);

		ResponseEntity<AnalysisDTO> response = restTemplate.getForEntity(
				createURLWithPortAndQueryParam("/api/webCrawler/v1/analyse", paramMap), AnalysisDTO.class);

		AnalysisDTO analysis = response.getBody();
		assertEquals(194, analysis.getNumLinks());
	}

	@SuppressWarnings("unused")
	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

	private String createURLWithPortAndQueryParam(String uri, Map<String, String> params) {
		StringBuilder strBuilder = new StringBuilder("http://localhost:");
		strBuilder.append(port);
		strBuilder.append(uri);
		Iterator<Map.Entry<String, String>> iter = params.entrySet().iterator();
		while (iter.hasNext()) {
			strBuilder.append("?");
			Map.Entry<String, String> entry = iter.next();
			strBuilder.append(entry.getKey());
			strBuilder.append("=");
			strBuilder.append(entry.getValue());
			if (iter.hasNext()) {
				strBuilder.append("&");
			}
		}

		return strBuilder.toString();
	}

}
