package com.scout24.webcrawler.dto;

import java.util.Map;

public class LinkDTO {

	private String url;

	private String redirectedStatus;

	private boolean available;

	private String status;

	private Map<String, String> error;

	public LinkDTO() {
		// TODO Auto-generated constructor stub
	}

	public LinkDTO(String url, String redirectedStatus, boolean available, String status, Map<String, String> error) {
		super();
		this.url = url;
		this.redirectedStatus = redirectedStatus;
		this.available = available;
		this.status = status;
		this.error = error;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRedirectedStatus() {
		return redirectedStatus;
	}

	public void setRedirectedStatus(String redirectedStatus) {
		this.redirectedStatus = redirectedStatus;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Map<String, String> getError() {
		return error;
	}

	public void setError(Map<String, String> error) {
		this.error = error;
	}

}
