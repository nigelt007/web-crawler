package com.nigel.webcrawler.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;

import org.apache.commons.validator.routines.UrlValidator;

/**
 * A utility class containing all the methods which are used for doing
 * operations related to the the Web server or the internet.
 * 
 * @author Nigel
 *
 */
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
