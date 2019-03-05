package com.visuallogictool.application.nodes.baseclassimpl;

import com.visuallogictool.application.jsonclass.NodeConfiguration;
import com.visuallogictool.application.messages.flow.NextActorReceived;
import com.visuallogictool.application.messages.flow.NextActors;

import akka.actor.ActorRef;
import akka.actor.AbstractActor.Receive;

public class ApiRestOutputConfiguration extends NodeConfiguration  {

	private int id;
	
	public ApiRestOutputConfiguration() {
		// TODO Auto-generated constructor stub
	}

	
	
	public ApiRestOutputConfiguration(int id) {
		super();
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

	
}
