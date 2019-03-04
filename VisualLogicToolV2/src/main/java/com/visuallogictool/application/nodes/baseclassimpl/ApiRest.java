package com.visuallogictool.application.nodes.baseclassimpl;

import java.util.ArrayList;

import com.visuallogictool.application.nodes.baseclass.InputNode;
import com.visuallogictool.application.server.Server;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;



public class ApiRest extends InputNode {
	
	 LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	 //final LoggingAdapter log = Logging.getLogger(getContext().getSystem().eventStream(), "my.string");
	  static public Props props(ApiRestConfiguration apiRestConfiguration) {
		    return Props.create(ApiRest.class, () -> new ApiRest(apiRestConfiguration));
	  }
	  static public Props props(int id, String api, ArrayList<ActorRef> listActor) {
		    return Props.create(ApiRest.class, () -> new ApiRest(id,api,listActor));
	  }

	private String api;
	
	
	public ApiRest(ApiRestConfiguration apiRestConfiguration) {
		super(apiRestConfiguration.getId());
		
		this.api = apiRestConfiguration.getApi();
		
		//"/application"
		
	}
	public ApiRest(int id, String api,ArrayList<ActorRef> listActor) {
		super(id);
		
		this.api = api;
		
		//"/application"
		this.setListNextActor(listActor);
		
	}
	
	@Override
	public void processMessage() {
		
	}

	@Override
	public void getGUI() {
		
	}

	@Override
	public Receive createReceive() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void createMessageTrigger() {
		System.out.println("In create message trigger");
		//Server.addRoute(this.api, this.getSelf());
		
	}
	@Override
	protected void initializeRunningPhase() {
		/*this.runningPhase = receiveBuilder().match(MessageReceived.class,apply -> {
			 log.debug("Starting");
			System.out.println("message Received");
			//this.getOutput().get(0).tell(new MessageReceived(apply.getJson()), ActorRef.noSender());;
		}).matchAny(apply->{
			log.debug("Received from server");
			System.out.println("Received from server " + apply.toString());
			System.out.println("In ApiRest : "+this.getOutput().get(0));
			this.getOutput().get(0).tell(new MessageReceived(apply.toString()), ActorRef.noSender());
			this.getSender().tell(new MessageFlowStart(5), this.getSelf());
		}					
				).build();*/
		
	}





}
