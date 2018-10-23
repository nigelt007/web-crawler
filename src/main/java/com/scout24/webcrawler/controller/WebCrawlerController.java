package com.scout24.webcrawler.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scout24.webcrawler.constants.ApiConstants;
import com.scout24.webcrawler.dto.AnalysisDTO;
import com.scout24.webcrawler.exceptions.InvalidInputException;
import com.scout24.webcrawler.service.WebCrawlerService;
import com.scout24.webcrawler.utils.WebUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * The REST API's regarding the Web Crawler will be handled by this controller.
 * 
 * @author Nigel
 *
 */
@RestController
@RequestMapping(ApiConstants.WEB_CRAWLER_CONTROLLER)
@Api(value = "webcrawlerController")
public class WebCrawlerController {

	private static final String ANALYSE_URL = "/analyse";

	private static final String PARAM_URI = "uri";

	@Autowired
	WebCrawlerService webCrawlerService;

	@ApiOperation(value = "Returns an analysis of the URL and the HTML content of the URL", response = AnalysisDTO.class)
	@GetMapping(value = ANALYSE_URL, produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Analysis of the URL is done"),
			@ApiResponse(code = 400, message = "The URL is not a valid HTTP URL") })
	public ResponseEntity<AnalysisDTO> analyseUrl(@RequestParam(name = PARAM_URI) String uri)
			throws InvalidInputException, IOException {
		System.out.println("Got here the url dude : " + uri);
		AnalysisDTO analysisDto = null;
		try {
			long startTime = System.currentTimeMillis();
			validateUri(uri);
			analysisDto = webCrawlerService.analyseInputUrl(uri);
			long endTime = System.currentTimeMillis();
			long timeTaken = (endTime - startTime) / (1000);
			System.out.println("took " + timeTaken + " seconds.");
		} catch (InvalidInputException e) {
			throw e;
		}
		return new ResponseEntity<AnalysisDTO>(analysisDto, HttpStatus.OK);
	}

	/**
	 * Validates whether the URL is a valid HTTP/HTTPS url.
	 * 
	 * @param uri
	 *            - the url for validating
	 * @throws InvalidInputException
	 *             - if the url provided is invalid.
	 */
	private void validateUri(String uri) throws InvalidInputException {
		if (!WebUtils.isValidUrl(uri)) {
			throw new InvalidInputException();
		}

	}

}
