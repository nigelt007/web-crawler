package com.scout24.webcrawler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scout24.webcrawler.service.WebCrawlerService;

@RestController
@RequestMapping("/api/webCrawler/v1")
public class WebCrawlerController {

	@Autowired
	private WebCrawlerService webCrawlerService;

	@GetMapping("/analyse")
	public ResponseEntity<Void> analyseUrl(@RequestParam(name = "uri") String uri) {
		System.out.println("Got here :" + uri);
		webCrawlerService.analyseUrl("www.google.com");
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
