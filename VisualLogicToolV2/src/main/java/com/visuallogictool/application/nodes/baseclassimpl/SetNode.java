package com.visuallogictool.application.nodes.baseclassimpl;

import java.util.ArrayList;
import java.util.HashMap;

import com.visuallogictool.application.messages.message.MessageNode;
import com.visuallogictool.application.messages.message.UnregisterRouter;
import com.visuallogictool.application.nodes.BaseNode;
import com.visuallogictool.application.nodes.information.Field;
import com.visuallogictool.application.nodes.information.NodeInformations;
import com.visuallogictool.application.nodes.information.NodeInformationsSetUp;
import com.visuallogictool.application.nodes.information.concrete.TextboxField;

import akka.actor.ActorRef;
import akka.actor.Props;



public class SetNode extends BaseNode {
	
	 

	private String var;
	private String value;
	private SetNodeConfiguration setNodeConfiguration;
	

	public SetNode(String id, String logId, String flowId, SetNodeConfiguration setNodeConfiguration) {
		super(id, logId, flowId);
		
		this.shortName = "SN";
		this.setLogName(flowId, logId);
		
		this.var = setNodeConfiguration.getVar();
		this.value = setNodeConfiguration.getValue();
		
		this.setNodeConfiguration = setNodeConfiguration;
		
		
		
	}


	@Override
	public void processMessage(HashMap<String, Object> context) {
		
		
		context.put(this.var, this.value);
		
		log.info("setting {} to {}", this.var, this.value);
		
		this.sendingToAllActor(context);	
		
	}
	
	public static NodeInformations getGUI() {
		
		NodeInformationsSetUp informations = getBaseInformation();
		informations = informations.setHeader("SetNode", "Set a variable of the context to X", "Set a variable of the context to X").
									setFields(new Field("var", "String", "variable", "name of the variable that you want to set")).
									setFields(new Field("value", "String", "value", "value of the variable you want to set"));
		
		informations = informations.setFieldBase(new TextboxField(null, "var", "var", false, 1, null)).
									setFieldBase(new TextboxField(null, "value", "value", false, 2, null));
		
		
		informations = informations.setClass("com.visuallogictool.application.nodes.baseclassimpl.SetNodeConfiguration"
											,"com.visuallogictool.application.nodes.baseclassimpl.SetNode");
		
		informations = informations.setShortName("SN");
		
		informations = informations.setImageUrl("https://img.icons8.com/material/24/000000/edit-property.png");
		
		return informations.getNodeInformations();
	}
	
	



}
