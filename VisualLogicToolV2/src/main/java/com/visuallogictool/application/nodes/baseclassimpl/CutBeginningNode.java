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
	
	 
	 //final LoggingAdapter log = Logging.getLogger(getContext().getSystem().eventStream(), "my.string");
	  static public Props props(String id, CutBeginningNodeConfiguration cutBeginningNodeConfiguration) {
		    return Props.create(CutBeginningNode.class, () -> new CutBeginningNode(id, cutBeginningNodeConfiguration));
	  }

	private String var;
	private int number;
	private CutBeginningNodeConfiguration cutBeginningNodeConfiguration;
	
	@Override
	public void preStart() throws Exception {
		// TODO Auto-generated method stub
		super.preStart();
		System.out.println("ID : " + this.id);
	}
	public CutBeginningNode(String id, CutBeginningNodeConfiguration cutBeginningNodeConfiguration) {
		super(id);
		
		this.var = cutBeginningNodeConfiguration.getVar();
		this.number = cutBeginningNodeConfiguration.getNumber();
		
		this.cutBeginningNodeConfiguration = cutBeginningNodeConfiguration;
		
		
		
	}


	@Override
	public void processMessage(HashMap<String, Object> context) {
		
		System.out.println("RECEIVED IN CUT BEGINNING");
		
		
		
		String variable = (String) context.get(this.var);
		
		System.out.println("BEFORE CUT : " + variable);
		
		variable = variable.substring(this.number);
		context.put(this.var, variable);
		
		System.out.println("AFTER CUT : " + variable);
		
		MessageNode messageToSend = new MessageNode(context);
	
		this.listNextActors.forEach(actor -> {
			actor.tell(messageToSend, ActorRef.noSender());
		});		
		
	}
	
	public static NodeInformations getGUI() {
		
		NodeInformationsSetUp informations = getBaseInformation();
		informations = informations.setHeader("CutBeginningNode", "Cut the beginning of the String", "Cut the beginning of the String").
									setFields(new Field("var", "String", "variable", "name of the variable that you want to modify")).
									setFields(new Field("number", "int", "number", "The number of characters you want to delete"));
		
		informations = informations.setFieldBase(new TextboxField(null, "var", "var", false, 1, null)).
									setFieldBase(new TextboxField(null, "number", "number", false, 2, null));
		
		
		informations = informations.setClass("com.visuallogictool.application.nodes.baseclassimpl.CutBeginningNodeConfiguration"
											,"com.visuallogictool.application.nodes.baseclassimpl.CutBeginningNode");
		
		informations = informations.setShortName("CBN");
		
		informations = informations.setImageUrl("https://img.icons8.com/material/24/000000/cut.png");
		
		return informations.getNodeInformations();
	}
	
	



}
