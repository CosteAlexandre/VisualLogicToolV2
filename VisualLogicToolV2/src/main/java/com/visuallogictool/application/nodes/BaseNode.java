package com.visuallogictool.application.nodes;

import java.util.ArrayList;
import java.util.HashMap;

import com.visuallogictool.application.messages.flow.NextActorReceived;
import com.visuallogictool.application.messages.flow.NextActors;
import com.visuallogictool.application.messages.flow.NodeCreated;
import com.visuallogictool.application.messages.message.MessageNode;
import com.visuallogictool.application.messages.message.MessageReceived;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;

public abstract class BaseNode extends AbstractActor{

	//public abstract static Props props();
	// create a method for this?
	
	
	//if every class sends a message back to the parent after a message is sent put a method here
	//public abstract void sendTimeParent()
	//with two other methods start time and stop time ?
	 
	/*
	private int numberOutput; // number of out put that a node has
	
	private boolean multipleOutPut; // Does this node has multiple output? Needed?
	private boolean singleOutput;
	*/
	//add method
	protected int id; // unique id for each node
	
	
	protected ArrayList<ActorRef> listNextActors; // list of the next actor he will call
	
	//add start/finish time
	
	//call this one with the new one to initialise quickly
	public BaseNode(int id ) {
		super();
		//this.numberOutput = numberOutput;		
		this.id = id;

				
	}
	
	public void setListNextActor(ArrayList<ActorRef> listNextActors) {
		this.listNextActors = listNextActors;
	}

	public void receivedOutput() {
		this.context().parent().tell(NextActorReceived.class, ActorRef.noSender());
	}
	
	public ArrayList<ActorRef> getOutput() {
		return this.listNextActors;
	}
	
	public abstract void processMessage(HashMap<String, Object> context);
	public abstract void processMessage(String message);
	
	public abstract void getGUI();// by introspection get all fields of class and send it back by formating it?
	// so all nodes sends the same response? And no multiple actions needed? Todo this add a description in class
	
	@Override
	public void preStart() throws Exception {
		getContext().getParent().tell(new NodeCreated(this.id), this.getSelf());
	}
	
	@Override
	public Receive createReceive() {
		// TODO Auto-generated method stub
		return receiveBuilder().match(NextActors.class, apply -> {
			//System.out.println("next actor received");
			this.listNextActors = apply.getListNextActor();
			this.getContext().getParent().tell(new NextActorReceived(), ActorRef.noSender());
		}).match(MessageReceived.class, apply -> {
			processMessage(apply.getMessage());
			
		}).match(MessageNode.class, apply -> {
			processMessage(apply.getContext());
		}).build();
		
	}
}
