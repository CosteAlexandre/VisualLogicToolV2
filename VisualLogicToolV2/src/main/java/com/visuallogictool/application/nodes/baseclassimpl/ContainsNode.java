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



public class ContainsNode extends TwoOutPutNode {
	
	 

	private String var;
	private String value;
	private ContainsNodeConfiguration containsNodeConfiguration;
	

	public ContainsNode(String id, String logId, String flowId, ContainsNodeConfiguration containsNodeConfiguration) {
		super(id, logId, flowId);
		
		this.shortName = "CondN";
		this.setLogName(flowId, logId);
		
		this.var = containsNodeConfiguration.getVar();
		this.value = containsNodeConfiguration.getValue();
		
		this.containsNodeConfiguration = containsNodeConfiguration;
		
		
		
	}


	@Override
	public void processMessage(HashMap<String, Object> context) {
		
		
		String variable = (String) context.get(this.var);
		
		
		MessageNode messageToSend = new MessageNode(context);
		
		int outPutNum;
		
		if(variable.contains(this.value)) {
			outPutNum = 0;
			log.debug("contains the value {}",this.value);
			
		}else {
			log.debug("doesn't contain the value {}",this.value);
			outPutNum = 1;
		}
		log.info("sending to output {}",outPutNum);
		this.listNextActors.get(outPutNum).forEach(output-> {
			output.tell(messageToSend, ActorRef.noSender());
		});
		
		
	
		
		
	}
	
	public static NodeInformations getGUI() {
		
		NodeInformationsSetUp informations = getBaseInformation();
		informations = informations.setHeader("ContainsNode", "Check if the String is contained in the variable", "Check if the String is contained in the variable").
									addFieldDescription(new Field("var", "String", "variable", "name of the variable that to check")).
									addFieldDescription(new Field("value", "String", "value", "the value that will be checked with the var"));
		
		informations = informations.addFieldDefinition(new TextboxField(null, "var", "var", false, 1, null)).
									addFieldDefinition(new TextboxField(null, "value", "value", false, 2, null));
		
		
		informations = informations.setClass("com.visuallogictool.application.nodes.baseclassimpl.ContainsNodeConfiguration"
											,"com.visuallogictool.application.nodes.baseclassimpl.ContainsNode");
		
		informations = informations.setShortName("CondN");
		
		informations = informations.setImageUrl("https://img.icons8.com/ultraviolet/40/000000/no-gluten.png");
		
		return informations.getNodeInformations();
	}
	
	



}
