package com.visuallogictool.application.nodes.baseclass;

import java.util.HashMap;

import com.visuallogictool.application.nodes.BaseNode;
import com.visuallogictool.application.nodes.information.NodeInformationsSetUp;

public abstract class OutputNode extends BaseNode{

	public OutputNode(String id, String logId ) {
		super(id, logId);		
	}

	public abstract void createMessageResponse(HashMap<String, Object> context); // Send a response back
	
	protected static String getColor() {
		return "rgb(187, 54, 54)";
	}	
	protected static String getType() {
		return "OutputNode";
	}
	protected static String getTypeDescription() {
		return "OutputNode";
	}
	protected static NodeInformationsSetUp getBaseInformation() {
		
		NodeInformationsSetUp informations = new NodeInformationsSetUp();
		
		informations = informations.setType(getType(),getTypeDescription());
		informations = informations.setColor(getColor());
		return informations;
	}
}
