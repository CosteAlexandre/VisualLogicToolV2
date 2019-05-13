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



public class AppendNode extends BaseNode {
	

	private String var;
	private String value;
	private String newVariable;
	private AppendNodeConfiguration appendNodeConfiguration;
	

	public AppendNode(String id, String logId, String flowId, AppendNodeConfiguration appendNodeConfiguration) {
		super(id, logId, flowId);
		
		this.shortName = "AN";
		this.setLogName(flowId, logId);
		
		this.var = appendNodeConfiguration.getVar();
		this.value = appendNodeConfiguration.getValue();
		this.newVariable = appendNodeConfiguration.getNewVariable();
		
		this.appendNodeConfiguration = appendNodeConfiguration;		
		
	}


	@Override
	public void processMessage(HashMap<String, Object> context) {
		
		String variable = (String) context.get(this.var);
		
		log.info("Appending node");
		log.debug("before appending : {}", variable);
		variable = variable + this.value;
		log.debug("After appending {}", variable);
		if(this.newVariable.equals("")) {
			context.put(this.var, variable);
			log.debug("erasing var");
		}else {
			context.put(this.newVariable, variable);
			log.debug("putting in new variable {}",this.newVariable);
		}
		
		this.sendingToAllActor(context);		
		
	}
	
	public static NodeInformations getGUI() {
		
		NodeInformationsSetUp informations = getBaseInformation();
		informations = informations.setHeader("AppendNode", "Append at the beginning of the String", "Append at the beginning of the String").
									addFieldDescription(new Field("var", "String", "variable", "name of the variable that you want to modify")).
									addFieldDescription(new Field("value", "String", "value", "the value that will be added at the beggining of the string")).
									addFieldDescription(new Field("newVariable", "String", "newVariable", "The new variable where the new information will be stored, if nothing it with erase the first variable"));
		
		informations = informations.addFieldDefinition(new TextboxField(null, "var", "var", false, 1, null)).
									addFieldDefinition(new TextboxField(null, "value", "value", false, 2, null)).
									addFieldDefinition(new TextboxField(null, "newVariable", "newVariable", false, 3, null));
		
		
		informations = informations.setClass("com.visuallogictool.application.nodes.baseclassimpl.AppendNodeConfiguration"
											,"com.visuallogictool.application.nodes.baseclassimpl.AppendNode");
		
		informations = informations.setShortName("AN");
		
		informations = informations.setImageUrl("https://img.icons8.com/color/50/000000/plus.png");
		
		return informations.getNodeInformations();
	}
	
	



}
