package com.visuallogictool.application.nodes.baseclass;

import com.visuallogictool.application.nodes.BaseNode;

public abstract class OutputNode extends BaseNode{

	public OutputNode(int id) {
		super(id);		
	}

	public abstract void createMessageResponse(); // Send a response back
	
	
}
