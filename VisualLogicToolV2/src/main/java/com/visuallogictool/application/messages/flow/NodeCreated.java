package com.visuallogictool.application.messages.flow;

public class NodeCreated {

	private String id;
	
	public NodeCreated(String id) {
		this.id = id ;
	}

	public String getId() {
		return id;
	}

	
	
/*
 * 
 * This is message for supervisor that indicates that the node has started well
 * 
 * Add this in abstract class of node for the prestart method
 * 
 */
}
