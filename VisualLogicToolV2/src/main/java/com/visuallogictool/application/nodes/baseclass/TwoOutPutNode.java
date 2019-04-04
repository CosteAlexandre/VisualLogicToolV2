package com.visuallogictool.application.nodes.baseclass;

import com.visuallogictool.application.nodes.BaseNode;
import com.visuallogictool.application.nodes.information.NodeInformationsSetUp;

public abstract class TwoOutPutNode extends BaseNode{

	public TwoOutPutNode(String id, String logId ) {
		super(id, logId);
		// TODO Auto-generated constructor stub
	}

	
	protected static String getColor() {
		// TODO Auto-generated method stub
		return "orange";
	}
	
	protected static String getType() {
		return "TwoOutPut";
	}
	protected static String getTypeDescription() {
		return "TwoOutPut";
	}
	
	protected static NodeInformationsSetUp getBaseInformation() {
			
			NodeInformationsSetUp informations = new NodeInformationsSetUp();
			
			informations = informations.setType(getType(),getTypeDescription());
			informations = informations.setColor(getColor());
			return informations;
	}
}
