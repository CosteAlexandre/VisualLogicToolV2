package com.visuallogictool.application.nodes.baseclassimpl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import com.visuallogictool.application.messages.message.MessageNode;
import com.visuallogictool.application.nodes.BaseNode;

import akka.actor.ActorRef;

public class CurrentHour extends BaseNode{



	private String var;
	private CurrentHourConfiguration configuration;
	
	public CurrentHour(int id, CurrentHourConfiguration configuration) {
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

	@Override
	public void getGUI() {
		// TODO Auto-generated method stub
		
	}

}
