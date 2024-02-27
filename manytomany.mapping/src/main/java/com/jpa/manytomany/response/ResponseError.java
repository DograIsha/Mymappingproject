package com.jpa.manytomany.response;

public class ResponseError {
	private String Errormessage;

	public ResponseError(String errormessage) {
		super();
		Errormessage = errormessage;
	}

	public String getErrormessage() {
		return Errormessage;
	}

	public void setErrormessage(String errormessage) {
		Errormessage = errormessage;
	}

}
