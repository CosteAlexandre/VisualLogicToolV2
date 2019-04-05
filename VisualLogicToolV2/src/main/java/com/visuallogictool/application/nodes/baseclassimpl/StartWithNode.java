package com.visuallogictool.application.nodes.baseclassimpl;

import java.util.ArrayDeque;
import java.util.HashMap;

import com.visuallogictool.application.messages.message.MessageNode;
import com.visuallogictool.application.nodes.baseclass.TwoOutPutNode;
import com.visuallogictool.application.nodes.information.Field;
import com.visuallogictool.application.nodes.information.NodeInformations;
import com.visuallogictool.application.nodes.information.NodeInformationsSetUp;
import com.visuallogictool.application.nodes.information.concrete.TextboxField;

import akka.actor.ActorRef;



public class StartWithNode extends TwoOutPutNode {
	
	 

	private String var;
	private String value;
	private StartWithNodeConfiguration startWithNodeConfiguration;
	

	public StartWithNode(String id, String logId, String flowId, StartWithNodeConfiguration startWithNodeConfiguration) {
		super(id, logId, flowId);
		
		this.shortName = "SWNO";
		this.setLogName(flowId, logId);
		
		this.var = startWithNodeConfiguration.getVar();
		this.value = startWithNodeConfiguration.getValue();
		
		this.startWithNodeConfiguration = startWithNodeConfiguration;
		
		
		
	}


	@Override
	public void processMessage(HashMap<String, Object> context) {
		
		
		
		
		String variable = (String) context.get(this.var);
		
		
		MessageNode messageToSend = new MessageNode(context);
		
		int outPutNum;
		
		if(variable.startsWith(this.value)) {
			log.debug("start with the value {}",this.value);
			outPutNum = 0;
			
		}else {
			log.debug("does not start with the value {}",this.value);
			outPutNum = 1;
		}
		log.info("sending to actor of output {}",outPutNum);
		this.listNextActors.get(outPutNum).forEach(output-> {
			output.tell(messageToSend, ActorRef.noSender());
		});
		ArrayDeque<String> queue = new ArrayDeque<String>();
		
		
		
	
		
		
	}
	
	public static NodeInformations getGUI() {
		
		NodeInformationsSetUp informations = getBaseInformation();
		informations = informations.setHeader("StartWithNode", "Check if the String is begins with the variable", "Check if the String is begins with the variable").
									setFields(new Field("var", "String", "variable", "name of the variable that to check")).
									setFields(new Field("value", "String", "value", "the value that will be checked with the var"));
		
		informations = informations.setFieldBase(new TextboxField(null, "var", "var", false, 1, null)).
									setFieldBase(new TextboxField(null, "value", "value", false, 2, null));
		
		
		informations = informations.setClass("com.visuallogictool.application.nodes.baseclassimpl.StartWithNodeConfiguration"
											,"com.visuallogictool.application.nodes.baseclassimpl.StartWithNode");
		
		informations = informations.setShortName("SWN");
		
		informations = informations.setImageUrl("https://img.icons8.com/android/40/000000/play.png");
		
		return informations.getNodeInformations();
	}
	
	



}
