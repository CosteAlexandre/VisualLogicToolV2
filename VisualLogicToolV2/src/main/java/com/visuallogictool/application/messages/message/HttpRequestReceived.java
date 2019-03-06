package com.visuallogictool.application.messages.message;

import com.fasterxml.jackson.databind.JsonNode;

import akka.http.javadsl.model.HttpRequest;

public class HttpRequestReceived {

	//sends message to the director with json content
	
	private HttpRequest request;

	public HttpRequestReceived(HttpRequest request) {
		this.request = request;
	}

	public HttpRequest getRequest() {
		return request;
	}


	
	
	
	
	
}
