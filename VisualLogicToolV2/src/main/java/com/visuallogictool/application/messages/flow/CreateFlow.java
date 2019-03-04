package com.visuallogictool.application.messages.flow;

import com.fasterxml.jackson.databind.JsonNode;

public class CreateFlow {
	/*
	 * 
	 * The director receives a message to create a new flow
	 * 
	 */

	private JsonNode json;

	public CreateFlow(JsonNode json) {
		super();
		this.json = json;
	}	
	
	public JsonNode getJson() {
		return json;
	}


	/*
	 * then director creates supervisor and in prestart of supervisor create all nodes
	 * 
	 * So in supervisor constructor add json for the rest of the nodes 
	 * 
	 * Check before sending to director if json is valid
	 * 
	 * 
	 */
	
	
	
	
	
	
}
