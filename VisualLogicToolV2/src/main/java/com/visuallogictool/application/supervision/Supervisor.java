package com.visuallogictool.application.supervision;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.visuallogictool.application.jsonclass.Flow;
import com.visuallogictool.application.jsonclass.Node;
import com.visuallogictool.application.messages.flow.FlowCreated;
import com.visuallogictool.application.messages.flow.NextActorReceived;
import com.visuallogictool.application.messages.flow.NextActors;
import com.visuallogictool.application.messages.flow.NodeCreated;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.DiagnosticLoggingAdapter;
import akka.event.Logging;
import akka.event.LoggingAdapter;


public class Supervisor extends AbstractActor{
	private Flow flow;
	private String id;
	private int actorReceived;
	private int nextActorReceived;
	
	private HashMap<String, ActorRef> actors;
	protected DiagnosticLoggingAdapter log = Logging.getLogger(this);
	
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
		
		Map<String, Object> mdc;
        mdc = new HashMap<String, Object>();
        mdc.put("supervisor", flow.getId());
	
        log.setMDC(mdc);
        log.clearMDC();
	}
	public Supervisor(Flow flow, ActorRef httpResponse) {
		this.flow = flow;
		this.id = flow.getId();
		this.actors = new HashMap<String, ActorRef>();
		
		this.actorReceived = 0;
		this.nextActorReceived = 0;
		this.httpResponse = httpResponse;
		
		Map<String, Object> mdc;
        mdc = new HashMap<String, Object>();
        mdc.put("supervisor", flow.getId());
        
	
        log.setMDC(mdc);
        
	}

	
	 
	@Override
	public void preStart() throws Exception {
		log.info("Supervisor {} started", this.id);
		this.flow.getListNode().forEach(node -> {
			try {
				log.info("Starting node {}", node.getShortName()+"-"+node.getLogId());
				this.getContext().actorOf(Props.create(Class.forName(node.getClassName()), node.getId(), node.getLogId(), this.id, node.getListParameters()),this.id+"-"+node.getShortName()+"-"+node.getLogId());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.debug("Error during class initialization of {}, exception : {}",node,e);
			}
		});
		
	}
	

	@Override
	public Receive createReceive() {

		return receiveBuilder().match(NodeCreated.class, apply -> {
			log.info("Node {} created of : {}", apply.getId(), this.getSender());
			nodeCreateReceive(apply.getId());
				
			
		}).match(NextActorReceived.class, apply -> {
			log.info("Next actor of received of {}",this.getSender());
			receiveNextActor();
		}).build();
		
	}

	private void receiveNextActor() {
		
		this.nextActorReceived++;
		if(this.nextActorReceived == this.flow.getListNode().size()) {
			log.info("All next actor Received ");
			if(this.httpResponse!=null) {				
				this.getContext().getParent().tell(new FlowCreated(this.id), this.httpResponse);
				log.info("Flow sucessfully created");
			}
		}
		
	}

	private void nodeCreateReceive(String id) {
		this.actorReceived++;
		this.actors.put(id, this.getSender());
		
		if(this.actorReceived == this.flow.getListNode().size()) {
			log.info("All node created ");
			
			int i =0;
			
			for (Node node : this.flow.getListNode()) {
				log.debug("Sending next actor to index {} in list and id {}",i,node.getId());
				sendNextActor(node);
				i++;
			}
			
			
			log.info("All next actor sent");
		}
	}

	private void sendNextActor(Node node) {
		ArrayList<ArrayList<ActorRef>> listNextActor = new ArrayList<ArrayList<ActorRef>>();
		node.getOutput().forEach(output -> {
			ArrayList<ActorRef> temp = new ArrayList<ActorRef>();
			output.forEach(child -> {
				temp.add(this.actors.get(child));
			});
			listNextActor.add(temp);
			
		});
		ActorRef actor = this.actors.get(node.getId());
		actor.tell(new NextActors(listNextActor), ActorRef.noSender());
		log.info("Sending next actor to {} ", node.getShortName()+"-"+node.getLogId());
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
