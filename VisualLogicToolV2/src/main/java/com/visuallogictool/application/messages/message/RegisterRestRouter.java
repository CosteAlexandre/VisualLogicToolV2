package com.visuallogictool.application.messages.message;

import akka.actor.ActorRef;

public class RegisterRestRouter {

	String api;
	ActorRef actor;
	public RegisterRestRouter(String api, ActorRef actor) {
		super();
		this.api = api;
		this.actor = actor;
	}
	public String getApi() {
		return api;
	}
	public ActorRef getActor() {
		return actor;
	}
	
	
	
	
	
	
}
