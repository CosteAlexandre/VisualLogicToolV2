package com.visuallogictool.application.nodes;

import java.util.ArrayList;
import java.util.HashMap;

import com.visuallogictool.application.messages.flow.NextActorReceived;
import com.visuallogictool.application.messages.flow.NextActors;
import com.visuallogictool.application.messages.flow.NodeCreated;
import com.visuallogictool.application.messages.message.HttpRequestReceived;
import com.visuallogictool.application.messages.message.MessageNode;
import com.visuallogictool.application.nodes.information.NodeInformations;
import com.visuallogictool.application.nodes.information.NodeInformationsSetUp;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.event.Logging;
import akka.event.LoggingAdapter;

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
	protected LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	
	protected String id; // unique id for each node
	
	
	protected ArrayList<ArrayList<ActorRef>> listNextActors; // list of the next actor he will call
	
	//add start/finish time
	
	//call this one with the new one to initialise quickly
	public BaseNode(String id ) {
		super();
		//this.numberOutput = numberOutput;		
		this.id = id;

				
	}
	
	public void setListNextActor(ArrayList<ArrayList<ActorRef>> listNextActors) {
		this.listNextActors = listNextActors;
	}

	public void receivedOutput() {
		this.context().parent().tell(NextActorReceived.class, ActorRef.noSender());
	}
	
	public ArrayList<ArrayList<ActorRef>> getOutput() {
		return this.listNextActors;
	}
	
	protected static String getColor() {
		return "rgb(255, 255, 0)";
	}
	
	protected static String getType() {
		return "BaseNode";
	}
	
	protected static String getTypeDescription() {
		return "BaseNode";
	}
	
	protected static NodeInformationsSetUp getBaseInformation() {
		
		NodeInformationsSetUp informations = new NodeInformationsSetUp();
		
		informations = informations.setType(getType(),getTypeDescription());
		informations = informations.setColor(getColor());
		return informations;
	}
	
	public abstract void processMessage(HashMap<String, Object> context);
	public void processMessage(String message) {
		HashMap<String, Object> context = new HashMap<String, Object>();
		context.put("message", message);
		processMessage(context);
	}
	
	public static NodeInformations getGUI() {
		return null;
	};// by introspection get all fields of class and send it back by formating it?
	// so all nodes sends the same response? And no multiple actions needed? Todo this add a description in class
	
	@Override
	public void preStart() throws Exception {
		log.info("Actor of {} class started : {}",this.getClass().getSimpleName(),this.getSelf());
		getContext().getParent().tell(new NodeCreated(this.id), this.getSelf());
	}
	
	@Override
	public Receive createReceive() {
		// TODO Auto-generated method stub
		return receiveBuilder().match(NextActors.class, apply -> {
			//System.out.println("next actor received");
			log.info("Message received NextActor.class");
			this.listNextActors = apply.getListNextActor();
			this.getContext().getParent().tell(new NextActorReceived(), this.getSelf());
		}).match(HttpRequestReceived.class, apply -> {
			log.info("Message received MessagedReceived.class");
			processMessage(apply.getRequest().toString());
			
		}).match(MessageNode.class, apply -> {
			log.info("Message received MessageNode.class");
			processMessage(apply.getContext());
		}).build();
		
	}
}
