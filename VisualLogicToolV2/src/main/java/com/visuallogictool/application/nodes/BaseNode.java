package com.visuallogictool.application.nodes;

import java.util.ArrayList;

import com.visuallogictool.application.messages.flow.NextActorReceived;
import com.visuallogictool.application.messages.flow.NextActors;
import com.visuallogictool.application.messages.flow.NodeCreated;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.AbstractActor.Receive;

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
	
	protected AbstractActor.Receive initialisationPhase;
	protected AbstractActor.Receive runningPhase;
	
	protected ArrayList<ActorRef> listNextActors; // list of the next actor he will call
	
	//add start/finish time
	
	//call this one with the new one to initialise quickly
	public BaseNode(int id ) {
		super();
		//this.numberOutput = numberOutput;		
		this.id = id;
		initializeRunningPhase();
		this.initialisationPhase = receiveBuilder().match(NextActors.class, apply -> {
			this.setListNextActor(apply.getListNextActor());
			this.receivedOutput();
			getContext().become(runningPhase);
		}).build();
				
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
	
	public abstract void processMessage();
	
	public abstract void getGUI();// by introspection get all fields of class and send it back by formating it?
	// so all nodes sends the same response? And no multiple actions needed? Todo this add a description in class
	
	@Override
	public void preStart() throws Exception {
		getContext().getParent().tell(new NodeCreated(), this.getSelf());
	}
	
	@Override
	public Receive createReceive() {
		
		return this.initialisationPhase;
		
		
	}
	protected abstract void initializeRunningPhase();
}
