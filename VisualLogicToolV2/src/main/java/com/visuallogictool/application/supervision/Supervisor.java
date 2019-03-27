package com.visuallogictool.application.supervision;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

import com.visuallogictool.application.jsonclass.Flow;
import com.visuallogictool.application.jsonclass.Node;
import com.visuallogictool.application.messages.flow.CreateFlow;
import com.visuallogictool.application.messages.flow.FlowCreated;
import com.visuallogictool.application.messages.flow.NextActorReceived;
import com.visuallogictool.application.messages.flow.NextActors;
import com.visuallogictool.application.messages.flow.NodeCreated;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.http.javadsl.model.HttpResponse;


public class Supervisor extends AbstractActor{
	private Flow flow;
	private String id;
	private int actorReceived;
	private int nextActorReceived;
	
	private HashMap<String, ActorRef> actors;
	private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	
	private ActorRef httpResponse;
	public static Props props(Flow flow, ActorRef httpResponse) {
		return Props.create(Supervisor.class, () -> new Supervisor(flow,httpResponse));
	}

	public Supervisor(Flow flow) {
		this.flow = flow;
		this.id = flow.getId();
		this.actors = new HashMap<String, ActorRef>();
		
		this.actorReceived = 0;
		this.nextActorReceived = 0;
	}
	public Supervisor(Flow flow, ActorRef httpResponse) {
		this.flow = flow;
		this.id = flow.getId();
		this.actors = new HashMap<String, ActorRef>();
		
		this.actorReceived = 0;
		this.nextActorReceived = 0;
		this.httpResponse = httpResponse;
	}

	
	 
	@Override
	public void preStart() throws Exception {
		log.info("Supervisor started");
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
			log.info("New node created of : {}",this.getSender());
			nodeCreateReceive(apply.getId());
				
			
		}).match(NextActorReceived.class, apply -> {
			log.info("Next actor of received : {}",this.getSender());
			receiveNextActor();
		}).build();
		
	}

	private void receiveNextActor() {
		
		this.nextActorReceived++;
		if(this.nextActorReceived == this.flow.getListNode().size()) {
			log.info("All next actor Received size node : ");
			if(this.httpResponse!=null) {
				this.getContext().getParent().tell(new FlowCreated(this.id), this.httpResponse);
			}
		}
		
	}

	private void nodeCreateReceive(String id) {
		this.actorReceived++;
		this.actors.put(id, this.getSender());
		
		if(this.actorReceived == this.flow.getListNode().size()) {
						
			log.info("All node created");
			this.flow.getListNode().forEach( node -> {
				sendNextActor(node);
			});
		}
	}

	private void sendNextActor(Node node) {
		ArrayList<ActorRef> listNextActor = new ArrayList<ActorRef>();
		
		node.getOutput().forEach(child -> {
		//	System.out.println(this.actors.get(child));
			listNextActor.add(this.actors.get(child));
		});
		ActorRef actor = this.actors.get(node.getId());
		actor.tell(new NextActors(listNextActor), ActorRef.noSender());
		
	}
	/*
	@Override
	public SupervisorStrategy supervisorStrategy() {
		// TODO Auto-generated method stub
		return new Function<Throwable, SupervisorStrategy.Directive>() {
            @Override
            public SupervisorStrategy.Directive apply(Throwable param) throws Exception {
                if (param instanceof
                        IllegalArgumentException)
                    return SupervisorStrategy.restart();
                if (param instanceof
                        ArithmeticException)
                    return SupervisorStrategy.resume();
                if (param instanceof
                        NullPointerException)
                    return SupervisorStrategy.stop();
                else return SupervisorStrategy.escalate();
            }
        };
	}*/

}
