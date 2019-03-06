package com.visuallogictool.application.nodes.baseclass;

import java.util.HashMap;

import com.visuallogictool.application.nodes.BaseNode;

public abstract class OutputNode extends BaseNode{

	public OutputNode(int id) {
		super(id);		
	}

	public abstract void createMessageResponse(HashMap<String, Object> context); // Send a response back
	
	
}
