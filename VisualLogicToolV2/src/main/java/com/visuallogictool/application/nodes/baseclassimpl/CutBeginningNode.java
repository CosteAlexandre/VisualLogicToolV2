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



public class CutBeginningNode extends BaseNode {
	
	 

	private String var;
	private int number;
	private String newVariable;
	
	private CutBeginningNodeConfiguration cutBeginningNodeConfiguration;
	

	public CutBeginningNode(String id, String logId, String flowId, CutBeginningNodeConfiguration cutBeginningNodeConfiguration) {
		super(id, logId, flowId);
		
		this.shortName = "CBN";
		this.setLogName(flowId, logId);
		
		this.var = cutBeginningNodeConfiguration.getVar();
		this.number = cutBeginningNodeConfiguration.getNumber();
		this.newVariable = cutBeginningNodeConfiguration.getNewVariable();
		
		this.cutBeginningNodeConfiguration = cutBeginningNodeConfiguration;
		
		
		
	}


	@Override
	public void processMessage(HashMap<String, Object> context) {
		
		String variable = (String) context.get(this.var);
		
		log.debug("Before cutting start {}",variable);
		
		variable = variable.substring(this.number);
		
		log.debug("After cutting start {}",variable);
		
		if(this.newVariable.equals("")) {
			log.info("errasing var");
			context.put(this.var, variable);
		}else {
			log.info("putting in new variable {}",this.newVariable);
			context.put(this.newVariable, variable);
		}
		
		this.sendingToAllActor(context);	
		
	}
	
	public static NodeInformations getGUI() {
		
		NodeInformationsSetUp informations = getBaseInformation();
		informations = informations.setHeader("CutBeginningNode", "Cut the beginning of the String", "Cut the beginning of the String").
									addFieldDescription(new Field("var", "String", "variable", "name of the variable that you want to modify")).
									addFieldDescription(new Field("number", "int", "number", "The number of characters you want to delete")).
									addFieldDescription(new Field("newVariable", "String", "newVariable", "The name of the new variable where the value will be stored"));
		
		informations = informations.addFieldDefinition(new TextboxField(null, "var", "var", false, 1, null)).
									addFieldDefinition(new TextboxField(null, "number", "number", false, 2, null)).
									addFieldDefinition(new TextboxField(null, "newVariable", "newVariable", false, 3, null));
		
		
		informations = informations.setClass("com.visuallogictool.application.nodes.baseclassimpl.CutBeginningNodeConfiguration"
											,"com.visuallogictool.application.nodes.baseclassimpl.CutBeginningNode");
		
		informations = informations.setShortName("CBN");
		
		informations = informations.setImageUrl("https://img.icons8.com/material/24/000000/cut.png");
		
		return informations.getNodeInformations();
	}
	
	



}
