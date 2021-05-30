package com.nigel.webcrawler.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.nigel.webcrawler.WebCrawlerApplicationTests;
import com.nigel.webcrawler.dto.AnalysisDTO;
import com.nigel.webcrawler.service.WebCrawlerServiceImpl;

/**
 * Unit tests for the service layer of Web Crawler.
 * 
 * @author Nigel
 *
 */
public class WebCrawlerServiceTest extends WebCrawlerApplicationTests {

	@Autowired
	WebCrawlerServiceImpl webCrawlerService;

	private static final String TEST_JSON_ORG_URL = "https://www.json.org";

	/**
	 * This test is to test whether the service layer is working. It will check by
	 * pinging the <a href="https://www.json.org">JSON</a> website and will check
	 * whether the number of links returned by the URL is 194.
	 * 
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAnalyseService_ForValidNumberOfLinks() throws Exception {
		AnalysisDTO analysisDto = webCrawlerService.analyseInputUrl(TEST_JSON_ORG_URL);
		assertEquals(194, analysisDto.getNumLinks());
	}
}
