package com.visuallogictool.application.supervision;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import com.visuallogictool.application.jsonclass.Flow;
import com.visuallogictool.application.messages.flow.NextActorReceived;
import com.visuallogictool.application.messages.flow.NextActors;
import com.visuallogictool.application.messages.flow.NodeCreated;
import com.visuallogictool.application.utils.Files;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;


public class Supervisor extends AbstractActor{
	private Flow flow;
	private int id;
	private int actorReceived;
	private int nextActorReceived;
	
	private HashMap<ActorRef, ArrayList<Integer>> childNextOutput;
	private HashMap<Integer, ActorRef> actors;
	
	public static Props props(Flow flow) {
		return Props.create(Supervisor.class, () -> new Supervisor(flow));
	}

	public Supervisor(Flow flow) {
		this.flow = flow;
		this.id = flow.getId();
		this.actors = new HashMap<Integer, ActorRef>();
		
		this.childNextOutput = new HashMap<ActorRef, ArrayList<Integer>>();
		
		this.actorReceived = 0;
		this.nextActorReceived = 0;
	}

	
	 
	@Override
	public void preStart() throws Exception {
		this.flow.getListNode().forEach(node -> {
			try {
				
				this.getContext().actorOf(Props.create(Class.forName(node.getClassName()), node.getId(), node.getListParameters()));
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
	}
	

	@Override
	public Receive createReceive() {

		return receiveBuilder().match(NodeCreated.class, apply -> {
			nodeCreateReceive(apply.getId());
				
			
		}).match(NextActorReceived.class, apply -> {
			receiveNextActor();
		}).build();
		
	}

	private void receiveNextActor() {
		
		this.nextActorReceived++;
		if(this.nextActorReceived == this.flow.getListNode().size()) {
			System.out.println("All next actor RECEIVED");
		}
		
	}

	private void nodeCreateReceive(int id) {
		this.actorReceived++;
		
		this.actors.put(id, this.getSender());
		this.childNextOutput.put(this.getSender(), this.flow.getListNode().get(id-1).getListNode());
		
		if(this.actorReceived == this.flow.getListNode().size()) {
			this.childNextOutput.forEach( (actor,list) -> {
				sendNextActor(actor);
			});
				

		}
		
	}

	private void sendNextActor(ActorRef actor) {
		ArrayList<ActorRef> listNextActor = new ArrayList<ActorRef>();
		
		if(this.childNextOutput.get(actor).isEmpty()) {
			this.nextActorReceived++;
			return ;
		}
		
		this.childNextOutput.get(actor).forEach(child -> {
			listNextActor.add(this.actors.get(child));
		});
		
		actor.tell(new NextActors(listNextActor), ActorRef.noSender());
		
	}

}
