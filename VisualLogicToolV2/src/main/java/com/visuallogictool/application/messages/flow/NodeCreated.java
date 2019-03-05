package com.visuallogictool.application.messages.flow;

public class NodeCreated {

	private int id;
	
	public NodeCreated(int id) {
		this.id = id ;
	}

	public int getId() {
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
