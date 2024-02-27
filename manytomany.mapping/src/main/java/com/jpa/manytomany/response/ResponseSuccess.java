package com.jpa.manytomany.response;

public class ResponseSuccess {

	private String successMessage;

	public ResponseSuccess(String successMessage) {
		super();
		this.successMessage = successMessage;
	}

	public String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

}
