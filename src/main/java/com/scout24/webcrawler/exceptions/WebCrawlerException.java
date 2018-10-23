package com.scout24.webcrawler.exceptions;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * Exception class used for displaying information regarding 
 * the exception which occured while accessing a url.
 * 
 * @author Nigel
 *
 */
public class WebCrawlerException implements Serializable {

	/** The generated serial version unique id */
	private static final long serialVersionUID = -6455658032926016013L;

	@ApiModelProperty(notes = "The Exception which occured while trying to access the url")
	private String exception;

	@ApiModelProperty(notes = "The detailed information regarding the exception which occured "
			+ "while trying to access the url.")
	private String message;

	public WebCrawlerException(String exception, String message) {
		this.exception = exception;
		this.message = message;

	}

	@Override
	public String toString() {
		String newLine = System.getProperty("line.separator");
		return "Exception occured : " + exception + newLine + " Message : " + message;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
