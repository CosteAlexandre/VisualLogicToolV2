package com.visuallogictool.application.server.route;

import com.visuallogictool.application.messages.flow.AddFlowGraph;
import com.visuallogictool.application.messages.message.HttpRequestReceived;

import akka.actor.Props;

public class AddFlowGraphRoute extends Route{

	public static Props props() {
	    return Props.create(AddFlowGraphRoute.class, () -> new AddFlowGraphRoute());
	}

	
	public AddFlowGraphRoute() {
		super();
	}
	
	@Override
	public Receive createReceive() {
		// TODO Auto-generated method stub
		return receiveBuilder().match(HttpRequestReceived.class, apply -> {
			
			String body  = getBody(apply);
			this.getContext().getSystem().actorSelection("/user/director").tell(new AddFlowGraph(body), this.getSender());
		 
		}).build();
		
	}
	
}
