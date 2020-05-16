package com.co.s4n.SuCorrientazoDomiclio.exception;

public class InvalidNameException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidNameException() {
		super();
	}
	
	public InvalidNameException(String message) {
		super(message);
	}
	
	public InvalidNameException(String message, Throwable ex) {
		super(message, ex);
	}
}
