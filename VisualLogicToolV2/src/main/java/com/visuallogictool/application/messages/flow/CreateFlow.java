package com.visuallogictool.application.messages.flow;

import com.visuallogictool.application.jsonclass.Flow;

public class CreateFlow {
	/*
	 * 
	 * The director receives a message to create a new flow
	 * 
	 */

	private Flow flow;

	public CreateFlow(Flow flow) {
		super();
		this.flow = flow;
	}	
	
	public Flow getFlow() {
		return flow;
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
