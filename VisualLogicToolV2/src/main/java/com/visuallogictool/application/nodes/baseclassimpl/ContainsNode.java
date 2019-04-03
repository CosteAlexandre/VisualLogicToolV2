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



public class ContainsNode extends TwoOutPutNode {
	
	 
	 //final LoggingAdapter log = Logging.getLogger(getContext().getSystem().eventStream(), "my.string");
	  static public Props props(String id, ContainsNodeConfiguration containsNodeConfiguration) {
		    return Props.create(ContainsNode.class, () -> new ContainsNode(id, containsNodeConfiguration));
	  }

	private String var;
	private String value;
	private ContainsNodeConfiguration containsNodeConfiguration;
	
	@Override
	public void preStart() throws Exception {
		// TODO Auto-generated method stub
		super.preStart();
		System.out.println("ID : " + this.id);
	}
	public ContainsNode(String id, ContainsNodeConfiguration containsNodeConfiguration) {
		super(id);
		
		this.var = containsNodeConfiguration.getVar();
		this.value = containsNodeConfiguration.getValue();
		
		this.containsNodeConfiguration = containsNodeConfiguration;
		
		
		
	}


	@Override
	public void processMessage(HashMap<String, Object> context) {
		
		System.out.println("RECEIVED IN Contains");
		
		
		
		String variable = (String) context.get(this.var);
		
		System.out.println("Contains PREPEND : " + variable);
		
		MessageNode messageToSend = new MessageNode(context);
		
		int outPutNum;
		
		if(variable.contains(this.value)) {
			outPutNum = 0;
			
		}else {
			outPutNum = 1;
		}
		this.listNextActors.get(outPutNum).forEach(output-> {
			output.tell(messageToSend, ActorRef.noSender());
		});
		System.out.println("AFTER Contains : " + variable);
		
		
	
		
		
	}
	
	public static NodeInformations getGUI() {
		
		NodeInformationsSetUp informations = getBaseInformation();
		informations = informations.setHeader("ContainsNode", "Check if the String is contained in the variable", "Check if the String is contained in the variable").
									setFields(new Field("var", "String", "variable", "name of the variable that to check")).
									setFields(new Field("value", "String", "value", "the value that will be checked with the var"));
		
		informations = informations.setFieldBase(new TextboxField(null, "var", "var", false, 1, null)).
									setFieldBase(new TextboxField(null, "value", "value", false, 2, null));
		
		
		informations = informations.setClass("com.visuallogictool.application.nodes.baseclassimpl.ContainsNodeConfiguration"
											,"com.visuallogictool.application.nodes.baseclassimpl.ContainsNode");
		
		informations = informations.setShortName("CondN");
		
		informations = informations.setImageUrl("https://img.icons8.com/ultraviolet/40/000000/no-gluten.png");
		
		return informations.getNodeInformations();
	}
	
	



}
