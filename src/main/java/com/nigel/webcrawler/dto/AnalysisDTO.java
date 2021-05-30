package com.nigel.webcrawler.dto;

import java.util.ArrayList;
import java.util.List;

import com.nigel.webcrawler.constants.HttpStatusConstants;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Model object which holds the analysis data of the web crawler.
 * 
 * It contains the : </br>
 * </br>
 * <b>url</b> - the Link url </br>
 * <b>numLinks</b> - the Number of links </br>
 * <b>status</b> - the Http Status code of the link </br>
 * <b>available</b> - the Availability of the link </br>
 * <b>internal</b> - the List of internal links </br>
 * <b>external</b> -the List of external links
 * 
 * @author Nigel
 *
 */
public class AnalysisDTO {

	@ApiModelProperty(notes = "The url which was provided for analysis")
	private String url;

	@ApiModelProperty(notes = "The number of urls which were found inside the html document of the url provided")
	private int numLinks;

	@ApiModelProperty(notes = "The Http Status Code of the url")
	private int status = HttpStatusConstants.HTTP_200_OK;

	@ApiModelProperty(notes = "An informational flag to show whether the url is available or not.")
	private boolean available;

	@ApiModelProperty(notes = "The list of internal urls found in the html page. Urls which are having the same domain.")
	private List<LinkDTO> internal = new ArrayList<>();

	@ApiModelProperty(notes = "The list of external urls found in the html page. Urls which are having different domain.")
	private List<LinkDTO> external = new ArrayList<>();

	public AnalysisDTO() {
		// TODO Auto-generated constructor stub
	}

	public AnalysisDTO(String url, int numLinks, int status, boolean available, List<LinkDTO> internal,
			List<LinkDTO> external) {
		super();
		this.url = url;
		this.numLinks = numLinks;
		this.status = status;
		this.available = available;
		this.internal = internal;
		this.external = external;
	}

	public static AnalysisDTOBuilder newBuilder() {
		return new AnalysisDTOBuilder();
	}

	public static class AnalysisDTOBuilder {

		private String url;

		private int numLinks;

		private int status;

		private boolean available;

		private List<LinkDTO> internal = new ArrayList<>();

		private List<LinkDTO> external = new ArrayList<>();

		public AnalysisDTOBuilder setUrl(String url) {
			this.url = url;
			return this;
		}

		public AnalysisDTOBuilder setNumberOfLinks(int numLinks) {
			this.numLinks = numLinks;
			return this;
		}

		public AnalysisDTOBuilder setStatus(int status) {
			this.status = status;
			return this;
		}

		public AnalysisDTOBuilder setAvailable(boolean available) {
			this.available = available;
			return this;
		}

		public AnalysisDTOBuilder setInternal(List<LinkDTO> internal) {
			this.internal = internal;
			return this;
		}

		public AnalysisDTOBuilder setExternal(List<LinkDTO> external) {
			this.external = external;
			return this;
		}

		public AnalysisDTO makeCandidateApplicationDTO() {
			return new AnalysisDTO(url, numLinks, status, available, internal, external);
		}
	}

	public AnalysisDTO(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getNumLinks() {
		return numLinks;
	}

	public void setNumLinks(int numLinks) {
		this.numLinks = numLinks;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public List<LinkDTO> getInternal() {
		return internal;
	}

	public void setInternal(List<LinkDTO> internal) {
		this.internal = internal;
	}

	public List<LinkDTO> getExternal() {
		return external;
	}

	public void setExternal(List<LinkDTO> external) {
		this.external = external;
	}
}
