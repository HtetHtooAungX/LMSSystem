package com.hha.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class TokenExpiredException extends LMSException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TokenExpiredException() {
		super("Token Expired!");
	}

}
