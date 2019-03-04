package com.visuallogictool.application.supervision;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import com.visuallogictool.application.jsonclass.Flow;
import com.visuallogictool.application.messages.flow.NextActors;
import com.visuallogictool.application.messages.flow.NodeCreated;
import com.visuallogictool.application.utils.Files;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;


public class Supervisor extends AbstractActor{
	private Flow flow;
	private int id;
	private ArrayList<ActorRef> actors;
	private HashMap<ActorRef, ArrayList<Integer>> childNextOutput;
	
	public static Props props(Flow flow) {
		return Props.create(Supervisor.class, () -> new Supervisor(flow));
	}

	public Supervisor(Flow flow) {
		this.flow = flow;
		this.id = flow.getId();
		this.actors = new ArrayList<ActorRef>();
		
		this.childNextOutput = new HashMap<ActorRef, ArrayList<Integer>>();
	}

	
	 
	@Override
	public void preStart() throws Exception {
		this.flow.getListNode().forEach(node -> {
			
			try {
				this.getContext().actorOf(Props.create(Class.forName(node.getClassName()), node.getListParameters()));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
	}
	
	
	
	

	@Override
	public Receive createReceive() {

		return receiveBuilder().matchAny(apply->{
			
			//System.out.println("In supervisor");
			
		}).build();
	}

}
