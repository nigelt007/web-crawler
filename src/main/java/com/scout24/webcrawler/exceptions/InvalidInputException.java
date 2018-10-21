package com.scout24.webcrawler.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "The input request is invalid")
public class InvalidInputException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3191360312911597209L;

	public InvalidInputException(String message) {
		super(message);
	}

	public InvalidInputException(InvalidInputException e) {
		super(e);
	}

	public InvalidInputException(Exception e) {
		super(e);
	}

}
