package com.scout24.webcrawler.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;

import org.apache.commons.validator.routines.UrlValidator;

public class WebUtils {

	/**
	 * This util method tests whether the url is a valid http url.
	 * 
	 * It uses Apache Common's validator framework to validate the url.
	 * 
	 * @param url
	 * @return
	 */
	public static boolean isValidUrl(String url) {
		UrlValidator validator = new UrlValidator();
		return validator.isValid(url);
	}

	/**
	 * 
	 * @param urlString
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static boolean isUrlReachable(String urlString) {
		try {
			return InetAddress.getByName(urlString).isReachable(500);
		} catch (IOException e) {
		}
		return false;
	}
}
