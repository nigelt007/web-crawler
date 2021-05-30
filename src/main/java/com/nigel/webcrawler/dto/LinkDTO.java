package com.nigel.webcrawler.dto;

import com.nigel.webcrawler.constants.HttpStatusConstants;
import com.nigel.webcrawler.exceptions.WebCrawlerException;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Model object which holds the link data of the web crawler.
 * 
 * It contains the : </br>
 * </br>
 * <b>url</b> - the Link url </br>
 * <b>redirectedStatus</b> - the Http status code of the redirected link. </br>
 * <b>redirectedUrl</b> - the final url to which the link was redirected. </br>
 * <b>available</b> - the Availability of the link </br>
 * 
 * @author Nigel
 *
 */
public class LinkDTO {

	@ApiModelProperty(notes = "The url which was found inside the url which was provided for analysis")
	private String url;

	@ApiModelProperty(notes = "The Redirection Http Status Code of the url which will be a 3xx status code, "
			+ "if the url was redirected. Or else it will be having the the 200 (OK) Http status code.")
	private int redirectedStatus = HttpStatusConstants.HTTP_200_OK;

	@ApiModelProperty(notes = "The final url to which the link url was redirected.")
	private String redirectedUrl;

	@ApiModelProperty(notes = "An informational flag to show whether the url is available or not.")
	private boolean available;

	@ApiModelProperty(notes = "The error description of the url, if any error had occured.")
	private WebCrawlerException error;

	public LinkDTO() {
		// TODO Auto-generated constructor stub
	}

	public LinkDTO(String url, int redirectedStatus, String redirectedUrl, boolean available,
			WebCrawlerException error) {
		super();
		this.url = url;
		this.redirectedStatus = redirectedStatus;
		this.redirectedUrl = redirectedUrl;
		this.available = available;
		this.error = error;
	}

	/**
	 * Returns a new instance of the {@link LinkDTOBuilder}
	 * @return
	 */
	public static LinkDTOBuilder newBuilder() {
		return new LinkDTOBuilder();
	}

	/**
	 * A builder used for building a LinkDTO.
	 *
	 */
	public static class LinkDTOBuilder {

		private String url;

		private int redirectedStatus;

		private String redirectedUrl;

		private boolean available;

		private WebCrawlerException error;

		public LinkDTOBuilder setUrl(String url) {
			this.url = url;
			return this;
		}

		public LinkDTOBuilder setRedirectedStatus(int redirectedStatus) {
			this.redirectedStatus = redirectedStatus;
			return this;
		}

		public LinkDTOBuilder setRedirectedUrl(String redirectedUrl) {
			this.redirectedUrl = redirectedUrl;
			return this;
		}

		public LinkDTOBuilder setAvailable(boolean available) {
			this.available = available;
			return this;
		}

		public LinkDTOBuilder setError(WebCrawlerException error) {
			this.error = error;
			return this;
		}

		public LinkDTO makeLinkDTO() {
			return new LinkDTO(url, redirectedStatus, redirectedUrl, available, error);
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getRedirectedStatus() {
		return redirectedStatus;
	}

	public void setRedirectedStatus(int redirectedStatus) {
		this.redirectedStatus = redirectedStatus;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public WebCrawlerException getError() {
		return error;
	}

	public void setError(WebCrawlerException error) {
		this.error = error;
	}

	public String getRedirectedUrl() {
		return redirectedUrl;
	}

	public void setRedirectedUrl(String redirectedUrl) {
		this.redirectedUrl = redirectedUrl;
	}

}
