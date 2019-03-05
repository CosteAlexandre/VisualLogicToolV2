package com.visuallogictool.application.supervision;

import java.util.ArrayList;
import java.util.HashMap;

import com.visuallogictool.application.jsonclass.Flow;
import com.visuallogictool.application.jsonclass.Node;
import com.visuallogictool.application.messages.flow.NextActorReceived;
import com.visuallogictool.application.messages.flow.NextActors;
import com.visuallogictool.application.messages.flow.NodeCreated;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;


public class Supervisor extends AbstractActor{
	private Flow flow;
	private int id;
	private int actorReceived;
	private int nextActorReceived;
	
	private HashMap<Integer, ActorRef> actors;
	
	public static Props props(Flow flow) {
		return Props.create(Supervisor.class, () -> new Supervisor(flow));
	}

	public Supervisor(Flow flow) {
		this.flow = flow;
		this.id = flow.getId();
		this.actors = new HashMap<Integer, ActorRef>();
		
		
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
		
		if(this.actorReceived == this.flow.getListNode().size()) {
			this.flow.getListNode().forEach( node -> {
				sendNextActor(node);
			});
				

		}
		
	}

	private void sendNextActor(Node node) {
		ArrayList<ActorRef> listNextActor = new ArrayList<ActorRef>();
		if(node.getListNode().isEmpty()) {
			this.nextActorReceived++;
			return ;
		}
		
		node.getListNode().forEach(child -> {
			listNextActor.add(this.actors.get(child));
		});
		ActorRef actor = this.actors.get(node.getId());
		actor.tell(new NextActors(listNextActor), ActorRef.noSender());
		
	}

}
