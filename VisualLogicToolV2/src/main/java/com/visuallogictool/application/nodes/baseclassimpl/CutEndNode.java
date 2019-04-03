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
	
	 
	 //final LoggingAdapter log = Logging.getLogger(getContext().getSystem().eventStream(), "my.string");
	  static public Props props(String id, CutEndNodeConfiguration cutEndNodeConfiguration) {
		    return Props.create(CutEndNode.class, () -> new CutEndNode(id, cutEndNodeConfiguration));
	  }

	private String var;
	private int number;
	private CutEndNodeConfiguration cutEndNodeConfiguration;
	
	@Override
	public void preStart() throws Exception {
		// TODO Auto-generated method stub
		super.preStart();
		System.out.println("ID : " + this.id);
	}
	public CutEndNode(String id, CutEndNodeConfiguration cutEndNodeConfiguration) {
		super(id);
		
		this.var = cutEndNodeConfiguration.getVar();
		this.number = cutEndNodeConfiguration.getNumber();
		
		this.cutEndNodeConfiguration = cutEndNodeConfiguration;
		
		
		
	}


	@Override
	public void processMessage(HashMap<String, Object> context) {
		
		System.out.println("RECEIVED IN CUT END");
		
		
		
		String variable = (String) context.get(this.var);
		
		System.out.println("BEFORE CUT : " + variable);
		
		variable = variable.substring(0, variable.length()-this.number);
		context.put(this.var, variable);
		
		System.out.println("AFTER CUT : " + variable);
		
		MessageNode messageToSend = new MessageNode(context);
	
		this.listNextActors.forEach(actor -> {
			actor.tell(messageToSend, ActorRef.noSender());
		});		
		
	}
	
	public static NodeInformations getGUI() {
		
		NodeInformationsSetUp informations = getBaseInformation();
		informations = informations.setHeader("CutEndNode", "Cut the end of the String", "Cut the end of the String").
									setFields(new Field("var", "String", "variable", "name of the variable that you want to modify")).
									setFields(new Field("number", "int", "number", "The number of characters you want to delete"));
		
		informations = informations.setFieldBase(new TextboxField(null, "var", "var", false, 1, null)).
									setFieldBase(new TextboxField(null, "number", "number", false, 2, null));
		
		
		informations = informations.setClass("com.visuallogictool.application.nodes.baseclassimpl.CutEndNodeConfiguration"
											,"com.visuallogictool.application.nodes.baseclassimpl.CutEndNode");
		
		informations = informations.setShortName("CEN");
		
		informations = informations.setImageUrl("https://img.icons8.com/ios/24/000000/sewing-scissors-filled.png");
		
		return informations.getNodeInformations();
	}
	
	



}
