package com.elhadj.health.dto;

public class RequestErrorDTO {
	private int statusCode = 500;
	private String message = "Server error, see an administrator";
	private String messageDescription = "Server error, see an administrator";
	
	public RequestErrorDTO() {}
	
	public RequestErrorDTO(int statusCode, String message, String messageDescription) {
		this.statusCode = statusCode;
		this.message = message;
		this.messageDescription = messageDescription;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageDescription() {
		return messageDescription;
	}

	public void setMessageDescription(String messageDescription) {
		this.messageDescription = messageDescription;
	}
}
