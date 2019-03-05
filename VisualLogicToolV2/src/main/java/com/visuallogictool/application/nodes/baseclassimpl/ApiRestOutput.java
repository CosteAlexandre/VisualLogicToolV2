package com.visuallogictool.application.nodes.baseclassimpl;

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
	public void processMessage() {
		
		
		System.out.println("message processed");
	}

	@Override
	public void getGUI() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Receive createReceive() {
		// TODO Auto-generated method stub
		return receiveBuilder().match(NextActors.class, apply -> {
			//System.out.println("next actor received");
			this.listNextActors = apply.getListNextActor();
			this.getContext().getParent().tell(NextActorReceived.class, ActorRef.noSender());
			
		}).build();
	}
	
	@Override
	protected void initializeRunningPhase() {
		/*this.runningPhase = receiveBuilder().match(MessageReceived.class,apply -> {
			processMessage();
		}).build();*/
		
	}
}
