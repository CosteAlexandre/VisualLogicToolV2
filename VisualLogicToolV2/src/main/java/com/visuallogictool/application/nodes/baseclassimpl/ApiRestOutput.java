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
	public void createMessageResponse() {
		
	}

	@Override
	public void processMessage(String message) {
		
		
	}
	@Override
	public void processMessage(HashMap<String, Object> context) {
		System.out.println("RECEIVED IN OUTPUT NODE");
		
	}
	@Override
	public void getGUI() {
		// TODO Auto-generated method stub
		
	}


}
