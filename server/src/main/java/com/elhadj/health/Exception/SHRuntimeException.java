package com.elhadj.health.Exception;

public class SHRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String messageDescription;
	
	public SHRuntimeException(String message) {
		super(message);
		this.messageDescription = message;
	}
	
	public SHRuntimeException(String message, String messageDescription) {
		super(message);
		this.messageDescription = messageDescription;
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
}
