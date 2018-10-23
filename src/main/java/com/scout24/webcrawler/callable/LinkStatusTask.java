package com.scout24.webcrawler.callable;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.scout24.webcrawler.constants.HttpStatusConstants;
import com.scout24.webcrawler.dto.LinkDTO;
import com.scout24.webcrawler.exceptions.WebCrawlerException;

/**
 * Callable for analysing the link.
 * 
 * @author Nigel
 *
 */
public class LinkStatusTask implements Callable<List<LinkDTO>> {

	private List<String> linkList;
	private List<LinkDTO> linkDtoList = new ArrayList<>();

	public LinkStatusTask(List<String> linkList) {
		this.linkList = linkList;
	}

	@Override
	public List<LinkDTO> call() throws Exception {
		for (String resource : linkList) {
			LinkDTO linkDto = new LinkDTO();
			linkDto = pingUrlForStatus(resource, linkDto);
			linkDtoList.add(linkDto);
		}
		return linkDtoList;
	}

	private LinkDTO pingUrlForStatus(String link, LinkDTO linkDto) {
		linkDto.setUrl(link);
		try {
			URL url = new URL(link);
			URL finalRedirectedUrl = getRedirectedUrl(url, linkDto);
			linkDto.setRedirectedUrl(finalRedirectedUrl.toString());
			linkDto.setAvailable(true);
		} catch (MalformedURLException e) {
			linkDto.setAvailable(false);
			return linkDto;
		}
		return linkDto;
	}

	/**
	 * Gets the final redirected url.
	 * 
	 * @return
	 */
	private URL getRedirectedUrl(URL url, LinkDTO linkDto) {
		try {
			HttpURLConnection con = (HttpURLConnection) (url.openConnection());
			con.setInstanceFollowRedirects(false);
			con.setConnectTimeout(50);
			con.connect();
			int resCode = con.getResponseCode();
			if (resCode == HttpURLConnection.HTTP_MULT_CHOICE || resCode == HttpURLConnection.HTTP_SEE_OTHER
					|| resCode == HttpURLConnection.HTTP_MOVED_PERM || resCode == HttpURLConnection.HTTP_MOVED_TEMP
					|| resCode == HttpURLConnection.HTTP_USE_PROXY) {
				String Location = con.getHeaderField("Location");
				if (Location.startsWith("/")) {
					Location = url.getProtocol() + "://" + url.getHost() + Location;
				}
				if (linkDto.getRedirectedStatus() == HttpStatusConstants.HTTP_200_OK) {
					linkDto.setRedirectedStatus(resCode);
				}
				return getRedirectedUrl(new URL(Location), linkDto);
			}
		} catch (IOException e) {
			linkDto.setAvailable(false);
		} catch (Exception e) {
			if (e instanceof SocketTimeoutException) {
				WebCrawlerException ex = new WebCrawlerException(getClass().getName(), "The Website timed out");
				linkDto.setError(ex);
			}
		}
		return url;

	}

}
