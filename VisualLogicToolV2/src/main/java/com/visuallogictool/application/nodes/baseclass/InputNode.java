package com.visuallogictool.application.nodes.baseclass;

import com.visuallogictool.application.nodes.BaseNode;

public abstract class InputNode extends BaseNode{

	public InputNode(int id) {
		super(id);		
	}

	public abstract void createMessageTrigger(); // Start a logic like a listen on a folder/file or on a api / mail etc
	
	public void preStart() throws Exception {
		super.preStart();
		createMessageTrigger();		
	}
	//add parameter to context to tell where to send it at the end for output node? or depends of type
}
