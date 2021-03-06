package com.visuallogictool.application.nodes.baseclassimpl;

import java.util.HashMap;

import com.visuallogictool.application.messages.message.MessageNode;
import com.visuallogictool.application.nodes.BaseNode;
import com.visuallogictool.application.nodes.baseclass.TwoOutPutNode;
import com.visuallogictool.application.nodes.information.Field;
import com.visuallogictool.application.nodes.information.NodeInformations;
import com.visuallogictool.application.nodes.information.NodeInformationsSetUp;
import com.visuallogictool.application.nodes.information.concrete.TextboxField;

import akka.actor.ActorRef;
import akka.actor.Props;



public class ReplaceWithNode extends BaseNode {
	
	 

	private String var;
	private String value;
	private String newValue;
	private String otherVar;
	
	private ReplaceWithNodeConfiguration replaceWithNodeConfiguration;
	

	public ReplaceWithNode(String id, String logId, String flowId, ReplaceWithNodeConfiguration replaceWithNodeConfiguration) {
		super(id, logId, flowId);
		
		this.shortName = "RWN";
		this.setLogName(flowId, logId);
		
		this.var = replaceWithNodeConfiguration.getVar();
		this.value = replaceWithNodeConfiguration.getValue();
		this.newValue = replaceWithNodeConfiguration.getNewValue();
		this.otherVar = replaceWithNodeConfiguration.getOtherVar();
		this.replaceWithNodeConfiguration = replaceWithNodeConfiguration;
		
		
		
	}


	@Override
	public void processMessage(HashMap<String, Object> context) {
		
		
		
		String variable = (String) context.get(this.var);
		
		log.info("Replacing node");
		log.debug("before replacing : {}", variable);	
		
		variable = variable.replace(this.value, this.newValue );
		
		log.debug("After replacing {}", variable);
		
		if(this.otherVar.equals("")) {
			context.put(this.var, variable);
			log.debug("erasing var");
		} else {
			context.put(this.otherVar, variable);
			log.debug("putting in new variable {}",this.otherVar);
		}
		
		this.sendingToAllActor(context);
		
	}
	
	public static NodeInformations getGUI() {
		
		NodeInformationsSetUp informations = getBaseInformation();
		informations = informations.setHeader("ReplaceWithNode", "Replace the part given by the variable with the given parameter", "Replace the part given by the variable with the given parameter").
									addFieldDescription(new Field("var", "String", "variable", "name of the variable that to replace")).
									addFieldDescription(new Field("value", "String", "value", "the value that will be found to replace")).
									addFieldDescription(new Field("newValue", "String", "newValue", "the value that will replace the given String"))	.
									addFieldDescription(new Field("otherVar", "String", "otherVar", "the otherVar of the context that will replace the given String"));
		
		informations = informations.addFieldDefinition(new TextboxField(null, "var", "var", false, 1, null)).
									addFieldDefinition(new TextboxField(null, "value", "value", false, 2, null)).
									addFieldDefinition(new TextboxField(null, "newValue", "newValue", false, 3, null)).
									addFieldDefinition(new TextboxField(null, "otherVar", "otherVar", false, 4, null));
		
		
		informations = informations.setClass("com.visuallogictool.application.nodes.baseclassimpl.ReplaceWithNodeConfiguration"
											,"com.visuallogictool.application.nodes.baseclassimpl.ReplaceWithNode");
		
		informations = informations.setShortName("RWN");
		
		informations = informations.setImageUrl("https://img.icons8.com/ios/50/000000/replace-filled.png");
		
		return informations.getNodeInformations();
	}
	
	



}
