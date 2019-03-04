package com.visuallogictool.application.messages.message;

import java.util.HashMap;

//Type of message for passing informations form a node to another

public class MessageNode<T> {

	private HashMap<String,T> context;

	public MessageNode(HashMap<String, T> context) {
		this.context = context;
	}

	public HashMap<String, T> getContext() {
		return context;
	}
	
	
	
}
