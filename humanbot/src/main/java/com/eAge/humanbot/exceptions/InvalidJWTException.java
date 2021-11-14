package com.eAge.humanbot.exceptions;

import com.auth0.jwt.exceptions.JWTVerificationException;

public class InvalidJWTException extends JWTVerificationException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2046459753510451992L;
	
	public InvalidJWTException(String msg) {
		super(msg);
	}

}
