package com.visuallogictool.application.server.route;

import com.visuallogictool.application.jsonclass.Flow;
import com.visuallogictool.application.messages.flow.CreateFlow;
import com.visuallogictool.application.messages.flow.DeleteFlow;
import com.visuallogictool.application.messages.message.HttpRequestReceived;
import com.visuallogictool.application.utils.JsonParser;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.http.javadsl.model.HttpEntity.Strict;

public class DeleteFlowRoute extends AbstractActor{

	public static Props props() {
	    return Props.create(DeleteFlowRoute.class, () -> new DeleteFlowRoute());
	}

	private JsonParser jsonParse;
	
	public DeleteFlowRoute() {

		jsonParse = new JsonParser();
		
	}
	
	@Override
	public Receive createReceive() {
		// TODO Auto-generated method stub
		return receiveBuilder().match(HttpRequestReceived.class, apply -> {
			Strict entity = (Strict) apply.getRequest().entity();
			System.out.println(" TEST "+entity.getData().decodeString("UTF-8"));
			String id = entity.getData().decodeString("UTF-8");
			
			
			
			System.out.println("DELETE FLOW RECEIVED");
			
			this.getContext().getSystem().actorSelection("/user/director").tell(new DeleteFlow(id), this.getSender());;
		}).build();
		
	}
	
}
