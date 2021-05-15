package com.elhadj.health.Exception;

public class SHRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String messageDescription;
	private int statusCode;
	
	public SHRuntimeException(String message) {
		super(message);
		this.messageDescription = message;
		this.statusCode  = 500;
	}
	
	public SHRuntimeException(int statusCode, String message, String messageDescription) {
		super(message);
		this.messageDescription = messageDescription;
		this.statusCode = statusCode;
	}


	public String getMessageDescription() {
		return messageDescription;
	}

	public void setMessageDescription(String messageDescription) {
		this.messageDescription = messageDescription;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
}
