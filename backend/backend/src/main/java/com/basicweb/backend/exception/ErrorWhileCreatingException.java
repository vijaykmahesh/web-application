package com.basicweb.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class ErrorWhileCreatingException  extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8629268539860886735L;

	public ErrorWhileCreatingException(String message) {
		super(message);
	}
}