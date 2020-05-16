package com.co.s4n.SuCorrientazoDomiclio.exception;

public class NotRoutesFoundInFile extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotRoutesFoundInFile() {
		super();
	}
	
	public NotRoutesFoundInFile(String message) {
		super(message);
	}
	
	public NotRoutesFoundInFile(String message, Throwable ex) {
		super(message, ex);
	}

}
