package com.scout24.webcrawler.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import com.scout24.webcrawler.exceptions.InvalidInputException;

@Service
public class WebCrawlerService {

	public void analyseUrl(final String url) throws InvalidInputException, IOException {
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			String title = doc.title();
			System.out.println("Document Title : " + title);
			String baseUrl = doc.baseUri();
			System.out.println("Base url : " + baseUrl);
		} catch (IOException e) {
			throw new IOException(e);
		} catch (IllegalArgumentException e) {
			throw new InvalidInputException(e);
		}
	}
}
