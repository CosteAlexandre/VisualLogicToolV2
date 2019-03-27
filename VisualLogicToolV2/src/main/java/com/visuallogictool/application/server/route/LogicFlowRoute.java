package com.visuallogictool.application.server.route;

import com.visuallogictool.application.jsonclass.Flow;
import com.visuallogictool.application.messages.flow.CreateFlow;
import com.visuallogictool.application.messages.message.HttpRequestReceived;
import com.visuallogictool.application.utils.JsonParser;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.http.javadsl.model.HttpEntity.Strict;

public class LogicFlowRoute extends AbstractActor{

	public static Props props() {
	    return Props.create(LogicFlowRoute.class, () -> new LogicFlowRoute());
	}

	private JsonParser jsonParse;
	
	public LogicFlowRoute() {

		jsonParse = new JsonParser();
		
	}
	
	@Override
	public Receive createReceive() {
		// TODO Auto-generated method stub
		return receiveBuilder().match(HttpRequestReceived.class, apply -> {
			Strict entity = (Strict) apply.getRequest().entity();
			System.out.println(" TEST "+entity.getData().decodeString("UTF-8"));
			String body = entity.getData().decodeString("UTF-8");
			
			Flow flow = jsonParse.jsonFlowConverter(body);
			
			System.out.println("FLOW RECEIVED");
			
			this.getContext().getSystem().actorSelection("/user/director").tell(new CreateFlow(flow), this.getSender());;
		}).build();
		
	}
	
}
