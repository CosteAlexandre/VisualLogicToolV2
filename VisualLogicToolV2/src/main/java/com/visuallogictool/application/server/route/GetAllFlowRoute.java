package com.visuallogictool.application.server.route;

import com.visuallogictool.application.messages.flow.GetAllFlow;
import com.visuallogictool.application.messages.message.HttpRequestReceived;

import akka.actor.Props;

public class GetAllFlowRoute extends Route{

	public static Props props() {
	    return Props.create(GetAllFlowRoute.class, () -> new GetAllFlowRoute());
	}

	
	public GetAllFlowRoute() {
		super();
	}
	
	@Override
	public Receive createReceive() {
		// TODO Auto-generated method stub
		return receiveBuilder().match(HttpRequestReceived.class, apply -> {
			this.getContext().getSystem().actorSelection("/user/director").tell(new GetAllFlow(), this.getSender());;
			
		 
		}).build();
		
	}
	
}
