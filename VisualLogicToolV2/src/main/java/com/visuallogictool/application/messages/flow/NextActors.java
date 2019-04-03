package com.visuallogictool.application.messages.flow;

import java.util.ArrayList;

import akka.actor.ActorRef;

//sends to all his nodes the list of the next actors the nodes can send messages to


public class NextActors {

	private ArrayList<ArrayList<ActorRef>> listNextActors; // list of the next actor the node can send a message to,
	//by order

	public NextActors(ArrayList<ArrayList<ActorRef>> listNextActor) {
		this.listNextActors = listNextActor;
	}

	public ArrayList<ArrayList<ActorRef>> getListNextActor() {
		return listNextActors;
	}
	
	
	
	
}
