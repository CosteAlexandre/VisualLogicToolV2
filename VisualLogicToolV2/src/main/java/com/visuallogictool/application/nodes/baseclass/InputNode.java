package com.visuallogictool.application.nodes.baseclass;

import com.visuallogictool.application.nodes.BaseNode;
import com.visuallogictool.application.nodes.information.NodeInformationsSetUp;

public abstract class InputNode extends BaseNode{

	public InputNode(String id , String logId, String flowId ) {
		super(id,logId, flowId);		
	}
	protected static String getColor() {
		// TODO Auto-generated method stub
		return "rgb(127, 255, 0)";
	}
	protected static String getType() {
		return "InputNode";
	}
	protected static String getTypeDescription() {
		return "InputNode";
	}
	protected static NodeInformationsSetUp getBaseInformation() {
		
		NodeInformationsSetUp informations = new NodeInformationsSetUp();
		
		informations = informations.setType(getType(),getTypeDescription());
		informations = informations.setColor(getColor());
		return informations;
	}
	
	public abstract void createMessageTrigger(); // Start a logic like a listen on a folder/file or on a api / mail etc
	
	public void preStart() throws Exception {
		super.preStart();
		createMessageTrigger();		
	}
	//add parameter to context to tell where to send it at the end for output node? or depends of type
}
