package com.visuallogictool.application.messages.flow;

public class GetFlowGraph {

	String id;
	
	public GetFlowGraph(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
}
