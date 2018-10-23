package com.scout24.webcrawler.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.scout24.webcrawler.callable.LinkStatusTask;
import com.scout24.webcrawler.dto.AnalysisDTO;
import com.scout24.webcrawler.dto.LinkDTO;
import com.scout24.webcrawler.exceptions.InvalidInputException;

/**
 * The WebCrawler service is where the actual operations related to parsing the
 * web page and analysing the web page is residing.
 * 
 * This service analyses the webpage and finds the urls which are located inside
 * the webpage. It checks which and groups all the internal and external urls
 * which are found in the web page.
 * 
 * It also checks whether the urls were redirected to some other pages and it
 * reports accordingly.
 * 
 * @author Nigel
 *
 */
@Service
public class WebCrawlerServiceImpl implements WebCrawlerService {

	private Document doc;
	private Connection.Response response = null;
	private List<String> internalUrls = new ArrayList<String>();
	private List<String> externalUrls = new ArrayList<String>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AnalysisDTO analyseInputUrl(final String url) throws InvalidInputException, IOException {

		AnalysisDTO analysisDto = new AnalysisDTO();
		analysisDto.setUrl(url);
		Connection.Response connResponse = null;
		try {
			connResponse = Jsoup.connect(url).execute();
			analysisDto.setAvailable(true);
			formAnalysisDTO(connResponse, analysisDto);
		} catch (Exception e) {
			throw new InvalidInputException(e);
		}
		return analysisDto;
	}

	/**
	 * Form the analysis response dto
	 * 
	 * @param connResponse
	 * @param analysisDto
	 * @throws IOException
	 */
	private void formAnalysisDTO(Connection.Response connResponse, AnalysisDTO analysisDto) throws IOException {
		response = connResponse;
		analysisDto.setStatus(getSitemapStatus());
		List<String> urls = getUrls();
		traceInternalAndExternalLinks(urls);
		analysisDto.setNumLinks(internalUrls.size() + externalUrls.size());
		pingAllUrlsForStatus(analysisDto);
	}

	/**
	 * Gets the status of the main url
	 * 
	 * @return
	 */
	private int getSitemapStatus() {
		return response.statusCode();
	}

	/**
	 * Gets the list of urls in the main urls html document after parsing the html
	 * document
	 * 
	 * @return
	 * @throws IOException
	 */
	private ArrayList<String> getUrls() throws IOException {
		ArrayList<String> urls = new ArrayList<String>();
		doc = response.parse();
		Elements links = doc.select("a[href]");

		for (Element link : links) {
			String href = link.absUrl("href");

			if (null != href)
				urls.add(href);
		}
		return urls;
	}

	/**
	 * Gets the total number of links ( external and internal) after parsing the
	 * main document.
	 * 
	 * @param hyperlinkCollection
	 * @return
	 */
	private void traceInternalAndExternalLinks(List<String> hyperlinkCollection) {

		try {
			URL aURL = new URL(doc.baseUri());
			String domain = aURL.getHost();

			for (String link : hyperlinkCollection) {
				if (link.contains(domain)) {
					internalUrls.add(link);
				} else {
					externalUrls.add(link);
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Pings the all urls for knowing the status
	 * 
	 * @param analysisDto
	 */
	private void pingAllUrlsForStatus(AnalysisDTO analysisDto) {

		analysisDto.getInternal().addAll(pingInternalUrlsForStatus());
		analysisDto.getExternal().addAll(pingExternalUrlsForStatus());

	}

	/**
	 * Pings the internal url for knowing their status.
	 * 
	 * Since the amount of urls which are there inside the page is huge, this is
	 * pinging and analysis of the url is done concurrently using Java's Executor
	 * Service.
	 * 
	 * @param internalUrl
	 * @param linkDto
	 */
	private List<LinkDTO> pingInternalUrlsForStatus() {
		List<LinkDTO> internalLinkDtoList = new ArrayList<>();
		final int numOfThread = 14;
		ExecutorService threadPool = Executors.newFixedThreadPool(numOfThread);

		List<Future<List<LinkDTO>>> futureTaskList = new ArrayList<>();

		List<List<String>> internalUrlsList = Lists.partition(internalUrls, numOfThread);

		for (List<String> subList : internalUrlsList) {
			LinkStatusTask task = new LinkStatusTask(subList);
			Future<List<LinkDTO>> futureTask = threadPool.submit(task);
			futureTaskList.add(futureTask);
		}
		for (Future<List<LinkDTO>> completeFutureTask : futureTaskList) {
			try {
				internalLinkDtoList.addAll(completeFutureTask.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}

		threadPool.shutdown();
		return internalLinkDtoList;
	}

	/**
	 * Pings the external urls for knowing their status.
	 * 
	 * Since the amount of urls which are there inside the page is huge, this is
	 * pinging and analysis of the url is done concurrently using Java's Executor
	 * Service.
	 * 
	 * @param externalUrl
	 * @param linkDto
	 */
	private List<LinkDTO> pingExternalUrlsForStatus() {
		List<LinkDTO> externalLinkDtoList = new ArrayList<>();
		final int numOfThread = 14;
		ExecutorService threadPool = Executors.newFixedThreadPool(numOfThread);

		List<Future<List<LinkDTO>>> futureTaskList = new ArrayList<>();

		List<List<String>> internalUrlsList = Lists.partition(externalUrls, numOfThread);

		for (List<String> subList : internalUrlsList) {
			LinkStatusTask task = new LinkStatusTask(subList);
			Future<List<LinkDTO>> futureTask = threadPool.submit(task);
			futureTaskList.add(futureTask);
		}
		for (Future<List<LinkDTO>> completeFutureTask : futureTaskList) {
			try {
				externalLinkDtoList.addAll(completeFutureTask.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}

		threadPool.shutdown();
		return externalLinkDtoList;
	}

}
