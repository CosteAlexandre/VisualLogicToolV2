package com.visuallogictool.application.nodes.baseclassimpl;

import java.util.HashMap;

import com.visuallogictool.application.messages.message.MessageNode;
import com.visuallogictool.application.nodes.baseclass.TwoOutPutNode;
import com.visuallogictool.application.nodes.information.Field;
import com.visuallogictool.application.nodes.information.NodeInformations;
import com.visuallogictool.application.nodes.information.NodeInformationsSetUp;
import com.visuallogictool.application.nodes.information.concrete.TextboxField;

import akka.actor.ActorRef;
import akka.actor.Props;



public class EndWithNode extends TwoOutPutNode {
	
	 

	private String var;
	private String value;
	private EndWithNodeConfiguration endWithNodeConfiguration;
	

	public EndWithNode(String id, String logId , EndWithNodeConfiguration endWithNodeConfiguration) {
		super(id, logId);
	
		this.shortName = "EWN";
		this.logName = this.shortName + "-" + logId;
		
		this.var = endWithNodeConfiguration.getVar();
		this.value = endWithNodeConfiguration.getValue();
		
		this.endWithNodeConfiguration = endWithNodeConfiguration;
		
		
		
	}


	@Override
	public void processMessage(HashMap<String, Object> context) {
		
		
		
		
		String variable = (String) context.get(this.var);
		
		
		MessageNode messageToSend = new MessageNode(context);
		
		int outPutNum;
		
		if(variable.endsWith(this.value)) {
			outPutNum = 0;
			
		}else {
			outPutNum = 1;
		}
		this.listNextActors.get(outPutNum).forEach(output-> {
			output.tell(messageToSend, ActorRef.noSender());
		});
		
		
	
		
		
	}
	
	public static NodeInformations getGUI() {
		
		NodeInformationsSetUp informations = getBaseInformation();
		informations = informations.setHeader("EndWithNode", "Check if the String ends with the variable", "Check if the String ends with the variable").
									setFields(new Field("var", "String", "variable", "name of the variable that to check")).
									setFields(new Field("value", "String", "value", "the value that will be checked with the var"));
		
		informations = informations.setFieldBase(new TextboxField(null, "var", "var", false, 1, null)).
									setFieldBase(new TextboxField(null, "value", "value", false, 2, null));
		
		
		informations = informations.setClass("com.visuallogictool.application.nodes.baseclassimpl.EndWithNodeConfiguration"
											,"com.visuallogictool.application.nodes.baseclassimpl.EndWithNode");
		
		informations = informations.setShortName("EWN");
		
		informations = informations.setImageUrl("https://img.icons8.com/ios/50/000000/close-window-filled.png");
		
		return informations.getNodeInformations();
	}
	
	



}
