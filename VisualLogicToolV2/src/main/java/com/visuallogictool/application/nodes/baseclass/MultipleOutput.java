package com.visuallogictool.application.nodes.baseclass;

import com.visuallogictool.application.nodes.BaseNode;
import com.visuallogictool.application.nodes.information.NodeInformationsSetUp;

public abstract class MultipleOutput extends BaseNode {

	public MultipleOutput(String id, String logId ) {
		super(id, logId);		
	}
	
	protected static String getColor() {
		// TODO Auto-generated method stub
		return "blue";
	}
	
	protected static String getType() {
		return "MultipleOutput";
	}
	protected static String getTypeDescription() {
		return "MultipleOutput";
	}
	
	protected static NodeInformationsSetUp getBaseInformation() {
			
			NodeInformationsSetUp informations = new NodeInformationsSetUp();
			
			informations = informations.setType(getType(),getTypeDescription());
			informations = informations.setColor(getColor());
			return informations;
	}
}
