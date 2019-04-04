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



public class CutEndNode extends BaseNode {
	
	 

	private String var;
	private int number;
	private String newVariable;
	
	private CutEndNodeConfiguration cutEndNodeConfiguration;
	

	public CutEndNode(String id, String logId, String flowId, CutEndNodeConfiguration cutEndNodeConfiguration) {
		super(id, logId);
		
		this.shortName = "CEN";
		this.setLogName(flowId, logId);
		
		this.var = cutEndNodeConfiguration.getVar();
		this.number = cutEndNodeConfiguration.getNumber();
		this.newVariable = cutEndNodeConfiguration.getNewVariable();
		
		this.cutEndNodeConfiguration = cutEndNodeConfiguration;
		
		
		
	}


	@Override
	public void processMessage(HashMap<String, Object> context) {
		
		
		
		
		String variable = (String) context.get(this.var);
		
		log.debug("Before cutting end {}",variable);
		
		variable = variable.substring(0, variable.length()-this.number);
		
		log.debug("After cutting end {}",variable);
		
		
		if(this.newVariable == "") {
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
		informations = informations.setHeader("CutEndNode", "Cut the end of the String", "Cut the end of the String").
									setFields(new Field("var", "String", "variable", "name of the variable that you want to modify")).
									setFields(new Field("number", "int", "number", "The number of characters you want to delete")).
									setFields(new Field("newVariable", "String", "newVariable", "The name of the new variable where the value will be stored"));
		
		informations = informations.setFieldBase(new TextboxField(null, "var", "var", false, 1, null)).
									setFieldBase(new TextboxField(null, "number", "number", false, 2, null)).
									setFieldBase(new TextboxField(null, "newVariable", "newVariable", false, 3, null));
		
		
		informations = informations.setClass("com.visuallogictool.application.nodes.baseclassimpl.CutEndNodeConfiguration"
											,"com.visuallogictool.application.nodes.baseclassimpl.CutEndNode");
		
		informations = informations.setShortName("CEN");
		
		informations = informations.setImageUrl("https://img.icons8.com/ios/24/000000/sewing-scissors-filled.png");
		
		return informations.getNodeInformations();
	}
	
	



}
