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
	
	 
	 //final LoggingAdapter log = Logging.getLogger(getContext().getSystem().eventStream(), "my.string");
	  static public Props props(String id, PrependNodeConfiguration prependNodeConfiguration) {
		    return Props.create(PrependNode.class, () -> new PrependNode(id, prependNodeConfiguration));
	  }

	private String var;
	private String value;
	private PrependNodeConfiguration prependNodeConfiguration;
	
	@Override
	public void preStart() throws Exception {
		// TODO Auto-generated method stub
		super.preStart();
		System.out.println("ID : " + this.id);
	}
	public PrependNode(String id, PrependNodeConfiguration prependNodeConfiguration) {
		super(id);
		
		this.var = prependNodeConfiguration.getVar();
		this.value = prependNodeConfiguration.getValue();
		
		this.prependNodeConfiguration = prependNodeConfiguration;
		
		
		
	}


	@Override
	public void processMessage(HashMap<String, Object> context) {
		
		System.out.println("RECEIVED IN CUT END");
		
		
		
		String variable = (String) context.get(this.var);
		
		System.out.println("BEFORE PREPEND : " + variable);
		
		variable = variable + this.value;
		context.put(this.var, variable);
		
		System.out.println("AFTER PREPEND : " + variable);
		
		MessageNode messageToSend = new MessageNode(context);
	
		this.listNextActors.forEach(actor -> {
			actor.tell(messageToSend, ActorRef.noSender());
		});		
		
	}
	
	public static NodeInformations getGUI() {
		
		NodeInformationsSetUp informations = getBaseInformation();
		informations = informations.setHeader("PrependNode", "Prepend at the end of the String", "Prepend at the end of the String").
									setFields(new Field("var", "String", "variable", "name of the variable that you want to modify")).
									setFields(new Field("value", "String", "value", "the value that will be added at the end of the string"));
		
		informations = informations.setFieldBase(new TextboxField(null, "var", "var", false, 1, null)).
									setFieldBase(new TextboxField(null, "value", "value", false, 2, null));
		
		
		informations = informations.setClass("com.visuallogictool.application.nodes.baseclassimpl.PrependNodeConfiguration"
											,"com.visuallogictool.application.nodes.baseclassimpl.PrependNode");
		
		informations = informations.setShortName("PN");
		
		informations = informations.setImageUrl("https://img.icons8.com/plasticine/48/000000/plus.png");
		
		return informations.getNodeInformations();
	}
	
	



}
