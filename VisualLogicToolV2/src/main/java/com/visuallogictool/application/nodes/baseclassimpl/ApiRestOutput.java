package com.visuallogictool.application.nodes.baseclassimpl;

import com.visuallogictool.application.nodes.baseclass.OutputNode;

import akka.actor.ActorRef;
import akka.actor.AbstractActor.Receive;

public class ApiRestOutput extends OutputNode{

	public ApiRestOutput(int id) {
		super(id);
		
	}

	@Override
	public void createMessageResponse() {
		
	}

	@Override
	public void processMessage() {
		
		
		System.out.println("message processed");
	}

	@Override
	public void getGUI() {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void initializeRunningPhase() {
		/*this.runningPhase = receiveBuilder().match(MessageReceived.class,apply -> {
			processMessage();
		}).build();*/
		
	}
}
