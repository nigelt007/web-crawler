package com.nigel.webcrawler.service;

import java.io.IOException;

import com.nigel.webcrawler.dto.AnalysisDTO;
import com.nigel.webcrawler.exceptions.InvalidInputException;

public interface WebCrawlerService {

	/**
	 * Analyse the URL and the internal and external urls which are found inside the
	 * web page of the url.
	 * 
	 * @param url
	 * @return
	 * @throws InvalidInputException
	 * @throws IOException
	 */
	AnalysisDTO analyseInputUrl(final String url) throws InvalidInputException, IOException;

}
