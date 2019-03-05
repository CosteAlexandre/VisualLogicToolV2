package com.visuallogictool.application.messages.message;

import java.util.HashMap;

//Type of message for passing informations form a node to another

public class MessageNode {

	private HashMap<String,Object> context;

	public MessageNode(HashMap<String, Object> context) {
		this.context = context;
	}

	public HashMap<String, Object> getContext() {
		return context;
	}
	
	
	
}
