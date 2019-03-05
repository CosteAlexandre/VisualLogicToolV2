package com.visuallogictool.application.nodes.baseclassimpl;

import java.util.ArrayList;

import com.visuallogictool.application.messages.flow.NextActorReceived;
import com.visuallogictool.application.messages.flow.NextActors;
import com.visuallogictool.application.messages.message.HttpRequestReceived;
import com.visuallogictool.application.messages.message.MessageReceived;
import com.visuallogictool.application.messages.message.RegisterRestRouter;
import com.visuallogictool.application.nodes.baseclass.InputNode;
import com.visuallogictool.application.server.RestServer;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.http.javadsl.model.HttpRequest;



public class ApiRest extends InputNode {
	
	 LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	 //final LoggingAdapter log = Logging.getLogger(getContext().getSystem().eventStream(), "my.string");
	  static public Props props(int id, ApiRestConfiguration apiRestConfiguration) {
		    return Props.create(ApiRest.class, () -> new ApiRest(id, apiRestConfiguration));
	  }
	  static public Props props(int id, String api, ArrayList<ActorRef> listActor) {
		    return Props.create(ApiRest.class, () -> new ApiRest(id,api,listActor));
	  }

	private String api;
	
	
	public ApiRest(int id, ApiRestConfiguration apiRestConfiguration) {
		super(1);
		
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
		return receiveBuilder().match(NextActors.class, apply -> {
			//System.out.println("next actor received");
			this.listNextActors = apply.getListNextActor();
			this.getContext().getParent().tell(new NextActorReceived(), ActorRef.noSender());
		}).match(MessageReceived.class, apply -> {
			System.out.println("RECEIVED IN API REST");
		}).build();
	}

	
	@Override
	public void createMessageTrigger() {
		System.out.println("In create message trigger");
		this.context().actorSelection("/user/restRouter").tell(new RegisterRestRouter(this.api, this.getSelf()), ActorRef.noSender());
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
