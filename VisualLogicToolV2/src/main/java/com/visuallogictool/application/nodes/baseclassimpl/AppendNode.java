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
	

	public AppendNode(String id, String logId , AppendNodeConfiguration appendNodeConfiguration) {
		super(id, logId );
		
		this.shortName = "AN";
		this.logName = this.shortName + "-" + logId;
		
		this.var = appendNodeConfiguration.getVar();
		this.value = appendNodeConfiguration.getValue();
		this.newVariable = appendNodeConfiguration.getNewVariable();
		
		this.appendNodeConfiguration = appendNodeConfiguration;
		
		
		
	}


	@Override
	public void processMessage(HashMap<String, Object> context) {
		
		
		
		
		String variable = (String) context.get(this.var);
		
		
		variable = this.value + variable;
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
		informations = informations.setHeader("AppendNode", "Append at the beginning of the String", "Append at the beginning of the String").
									setFields(new Field("var", "String", "variable", "name of the variable that you want to modify")).
									setFields(new Field("value", "String", "value", "the value that will be added at the beggining of the string")).
									setFields(new Field("newVariable", "String", "newVariable", "The new variable where the new information will be stored, if nothing it with erase the first variable"));
		
		informations = informations.setFieldBase(new TextboxField(null, "var", "var", false, 1, null)).
									setFieldBase(new TextboxField(null, "value", "value", false, 2, null)).
									setFieldBase(new TextboxField(null, "newVariable", "newVariable", false, 3, null));
		
		
		informations = informations.setClass("com.visuallogictool.application.nodes.baseclassimpl.AppendNodeConfiguration"
											,"com.visuallogictool.application.nodes.baseclassimpl.AppendNode");
		
		informations = informations.setShortName("AN");
		
		informations = informations.setImageUrl("https://img.icons8.com/color/50/000000/plus.png");
		
		return informations.getNodeInformations();
	}
	
	



}
