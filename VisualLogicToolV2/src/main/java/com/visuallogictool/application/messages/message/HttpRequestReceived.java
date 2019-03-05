package com.visuallogictool.application.messages.message;

import com.fasterxml.jackson.databind.JsonNode;

public class HttpRequestReceived {

	//sends message to the director with json content
	
	private String api;
	private String message;

	public HttpRequestReceived(String api, String message) {
		this.api = api;
		this.message = message;
	}

	public String getApi() {
		return api;
	}
	public String getMessage() {
		return message;
	}
	
	
	
	
}
