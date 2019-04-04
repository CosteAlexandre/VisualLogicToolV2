package com.visuallogictool.application.nodes.baseclassimpl;

import java.util.HashMap;

import com.visuallogictool.application.messages.message.MessageNode;
import com.visuallogictool.application.nodes.BaseNode;
import com.visuallogictool.application.nodes.information.Field;
import com.visuallogictool.application.nodes.information.NodeInformations;
import com.visuallogictool.application.nodes.information.NodeInformationsSetUp;
import com.visuallogictool.application.nodes.information.concrete.TextboxField;

import akka.actor.ActorRef;
import akka.actor.Props;



public class PrependNode extends BaseNode {
	
	 

	private String var;
	private String value;
	private String newVariable;
	private PrependNodeConfiguration prependNodeConfiguration;
	

	public PrependNode(String id, String logId , PrependNodeConfiguration prependNodeConfiguration) {
		super(id, logId);
		
		this.shortName = "PN";
		this.logName = this.shortName + "-" + logId;
		
		this.var = prependNodeConfiguration.getVar();
		this.value = prependNodeConfiguration.getValue();
		this.newVariable = prependNodeConfiguration.getNewVariable();
		
		this.prependNodeConfiguration = prependNodeConfiguration;
		
		
		
	}


	@Override
	public void processMessage(HashMap<String, Object> context) {
		
		
		
		
		String variable = (String) context.get(this.var);
		
		
		variable = variable + this.value;

		if(this.newVariable == "") {
			context.put(this.var, variable);
		}else {
			context.put(this.newVariable, variable);
		}
		
		
		MessageNode messageToSend = new MessageNode(context);
	
		this.listNextActors.forEach(output -> {
			output.forEach(actor -> {
				actor.tell(messageToSend, ActorRef.noSender());
			});
			
		});		
		
	}
	
	public static NodeInformations getGUI() {
		
		NodeInformationsSetUp informations = getBaseInformation();
		informations = informations.setHeader("PrependNode", "Prepend at the end of the String", "Prepend at the end of the String").
									setFields(new Field("var", "String", "variable", "name of the variable that you want to modify")).
									setFields(new Field("value", "String", "value", "the value that will be added at the end of the string")).
									setFields(new Field("newVariable", "String", "newVariable", "the name of the new variable where the value will be stored"));
		
		informations = informations.setFieldBase(new TextboxField(null, "var", "var", false, 1, null)).
									setFieldBase(new TextboxField(null, "value", "value", false, 2, null)).
									setFieldBase(new TextboxField(null, "newVariable", "newVariable", false, 3, null));
		
		
		informations = informations.setClass("com.visuallogictool.application.nodes.baseclassimpl.PrependNodeConfiguration"
											,"com.visuallogictool.application.nodes.baseclassimpl.PrependNode");
		
		informations = informations.setShortName("PN");
		
		informations = informations.setImageUrl("https://img.icons8.com/plasticine/48/000000/plus.png");
		
		return informations.getNodeInformations();
	}
	
	



}
