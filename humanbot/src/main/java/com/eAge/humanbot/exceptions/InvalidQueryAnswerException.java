package com.eAge.humanbot.exceptions;

public class InvalidQueryAnswerException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8265714500998067345L;
	
	public InvalidQueryAnswerException(String msg) {
		super(msg);
	}

}
