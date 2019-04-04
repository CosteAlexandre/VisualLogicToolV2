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
	
	public CurrentHour(String id, String logId, String flowId, CurrentHourConfiguration configuration) {
		super(id, logId);
		
		this.shortName = "CH";
		this.setLogName(flowId, logId);
		
		this.var = configuration.getVar();
	}
	
	
	@Override
	public void processMessage(HashMap<String, Object> context) {
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	    String hour = sdf.format(cal.getTime());
		context.put(var, hour);
		log.info("Current hour is {}",hour);
		this.sendingToAllActor(context);
	}

	public static NodeInformations getGUI() {
		
		NodeInformationsSetUp informations = getBaseInformation();
		informations = informations.setHeader("CurrentHour", "take the current hour", "Put the current hour in a variable").
									setFields(new Field("var", "hour", "variable", "the name of the variable where the new parameter will be stored"));
		
		informations = informations.setFieldBase(new TextboxField(null, "var", "var", false, 1, null));
	
		informations = informations.setClass("com.visuallogictool.application.nodes.baseclassimpl.CurrentHourConfiguration"
				,"com.visuallogictool.application.nodes.baseclassimpl.CurrentHour");
		
		informations = informations.setShortName("CH");
		
		informations = informations.setImageUrl("https://img.icons8.com/ios/50/000000/clock.png");
		
		return informations.getNodeInformations();
	}

}
