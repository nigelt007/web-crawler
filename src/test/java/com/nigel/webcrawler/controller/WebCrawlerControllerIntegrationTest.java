package com.nigel.webcrawler.controller;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.nigel.webcrawler.dto.AnalysisDTO;

/**
 * Integration tests for the REST and Service Layer.
 * 
 * @author Nigel
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebCrawlerControllerIntegrationTest {

	@LocalServerPort
	private int port;

	@Autowired
	TestRestTemplate restTemplate = new TestRestTemplate();

	private static final String TEST_JSON_ORG_URL = "https://www.json.org";

	private static final String LOCAL_HOST_URL = "http://localhost:";

	private static final String ANALYSE_REST_URI = "/api/webCrawler/v1/analyse";

	private static final String QUERY_PARAM_URI = "uri";

	/**
	 * The test is to test for Controller - Service wiring channel. The scenario is
	 * to check whether the number of links from the
	 * <a href="https://www.json.org">JSON</a> website is equal to 194.
	 * 
	 * @throws JSONException
	 */
	@Test
	public void testService_JsonOrgUrl() throws JSONException {

		Map<String, String> paramMap = new HashMap<>();
		paramMap.put(QUERY_PARAM_URI, TEST_JSON_ORG_URL);

		ResponseEntity<AnalysisDTO> response = restTemplate
				.getForEntity(createURLWithPortAndQueryParam(ANALYSE_REST_URI, paramMap), AnalysisDTO.class);

		AnalysisDTO analysis = response.getBody();
		assertEquals(194, analysis.getNumLinks());
	}

	/**
	 * Forms a link by concatenating the Local host, the server port number, the uri
	 * and the list of query parameters
	 * 
	 * @param uri
	 *            - the REST-API base uri to be used for
	 * @param params
	 * @return
	 */
	private String createURLWithPortAndQueryParam(String uri, Map<String, String> params) {
		StringBuilder strBuilder = new StringBuilder(LOCAL_HOST_URL);
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
