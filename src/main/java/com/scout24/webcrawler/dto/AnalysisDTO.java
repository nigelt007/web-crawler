package com.scout24.webcrawler.dto;

/**
 * The 
 * @author Nigel
 *
 */
public class AnalysisDTO {

	private String url;

	private int numLinks;

	private LinkDTO internal;

	private LinkDTO external;

	public AnalysisDTO() {
		// TODO Auto-generated constructor stub
	}

	public AnalysisDTO(String url, int numLinks, LinkDTO internal, LinkDTO external) {
		super();
		this.url = url;
		this.numLinks = numLinks;
		this.internal = internal;
		this.external = external;
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

	public LinkDTO getInternal() {
		return internal;
	}

	public void setInternal(LinkDTO internal) {
		this.internal = internal;
	}

	public LinkDTO getExternal() {
		return external;
	}

	public void setExternal(LinkDTO external) {
		this.external = external;
	}

}
