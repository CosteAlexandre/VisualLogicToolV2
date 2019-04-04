package com.visuallogictool.application.server.route;

import com.visuallogictool.application.jsonclass.Flow;
import com.visuallogictool.application.messages.flow.CreateFlow;
import com.visuallogictool.application.messages.message.HttpRequestReceived;

import akka.actor.Props;

public class LogicFlowRoute extends Route{

	public static Props props() {
	    return Props.create(LogicFlowRoute.class, () -> new LogicFlowRoute());
	}
	
	public LogicFlowRoute() {
		super();
	}
	
	@Override
	public Receive createReceive() {
		// TODO Auto-generated method stub
		return receiveBuilder().match(HttpRequestReceived.class, apply -> {
			
			String body  = getBody(apply);
			
			Flow flow = jsonParser.jsonFlowConverter(body);
			
			
			this.getContext().getSystem().actorSelection("/user/director").tell(new CreateFlow(flow), this.getSender());;
		}).build();
		
	}
	
}
