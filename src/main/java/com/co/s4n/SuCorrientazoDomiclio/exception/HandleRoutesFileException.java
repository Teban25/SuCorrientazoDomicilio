package com.co.s4n.SuCorrientazoDomiclio.exception;

public class HandleRoutesFileException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HandleRoutesFileException(String message) {
		super(message);
	}
	
	public HandleRoutesFileException(String message, Throwable ex) {
		super(message, ex);
	}
}
