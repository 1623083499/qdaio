package com.wb.exceptions;

public class BusinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BusinessException(){
		super();
	}
	public BusinessException(String msg){
		super(msg);
	}
	public BusinessException(Throwable cause){
		super(cause);
	}
	public BusinessException(String msg, Throwable cause){
		super(msg,cause);
	}
}
