package com.visuallogictool.application.messages.message;

import com.fasterxml.jackson.databind.JsonNode;

public class MessageReceived {

	//sends message to the director with json content
	
	private String json;

	public MessageReceived(String json) {
		this.json = json;
	}

	public String getJson() {
		return json;
	}
	
	
	
	
}
