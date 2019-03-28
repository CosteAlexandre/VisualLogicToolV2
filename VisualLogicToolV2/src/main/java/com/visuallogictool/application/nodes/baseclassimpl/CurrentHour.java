package com.visuallogictool.application.nodes.baseclassimpl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import com.visuallogictool.application.messages.message.MessageNode;
import com.visuallogictool.application.nodes.BaseNode;
import com.visuallogictool.application.nodes.information.Field;
import com.visuallogictool.application.nodes.information.NodeInformations;
import com.visuallogictool.application.nodes.information.NodeInformationsSetUp;
import com.visuallogictool.application.nodes.information.concrete.TextboxField;

import akka.actor.ActorRef;

public class CurrentHour extends BaseNode{



	private String var;
	private CurrentHourConfiguration configuration;
	
	public CurrentHour(String id, CurrentHourConfiguration configuration) {
		super(id);
		this.var = configuration.getVar();
	}
	
	
	@Override
	public void processMessage(HashMap<String, Object> context) {
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	    String hour = sdf.format(cal.getTime());
	    System.out.println("HOUR : " + hour);
		context.put(var, hour);
		this.getOutput().forEach( node -> {
			node.tell(new MessageNode(context), ActorRef.noSender());
		});
	}

	public static NodeInformations getGUI() {
		
		NodeInformationsSetUp informations = new NodeInformationsSetUp();
		informations = informations.setHeader("CurrentHour", "take the current hour", "Put the current hour in a variable").
									setFields(new Field("var", "hour", "variable", "the name of the variable where the new parameter will be stored"));
		
		informations = informations.setFieldBase(new TextboxField(null, "var", "var", true, 1, null));
	
		informations = informations.setType("BaseNode","BaseNode");
		
		informations = informations.setClass("com.visuallogictool.application.nodes.baseclassimpl.CurrentHourConfiguration"
				,"com.visuallogictool.application.nodes.baseclassimpl.CurrentHour");
		
		informations = informations.setShortName("CH");
		
		return informations.getNodeInformations();
	}

}
