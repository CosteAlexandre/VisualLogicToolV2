package com.visuallogictool.application.nodes.baseclassimpl;

import java.util.HashMap;

import com.visuallogictool.application.messages.flow.NextActorReceived;
import com.visuallogictool.application.messages.flow.NextActors;
import com.visuallogictool.application.nodes.baseclass.OutputNode;

import akka.actor.ActorRef;
import akka.actor.AbstractActor.Receive;

public class ApiRestOutput extends OutputNode{

	private ApiRestOutputConfiguration configuration;
	
	public ApiRestOutput(int id,ApiRestOutputConfiguration configuration) {
		super(id);
		this.configuration = configuration;
	}

	@Override
	public void createMessageResponse(HashMap<String, Object> context) {
		ActorRef actor = ((ActorRef)context.get("InputSender"));
		actor.tell("CALL BACK", ActorRef.noSender());
	}

	@Override
	public void processMessage(HashMap<String, Object> context) {
		System.out.println("RECEIVED IN OUTPUT NODE");
		createMessageResponse(context);
		
	}
	@Override
	public void getGUI() {
		// TODO Auto-generated method stub
		
	}


}
