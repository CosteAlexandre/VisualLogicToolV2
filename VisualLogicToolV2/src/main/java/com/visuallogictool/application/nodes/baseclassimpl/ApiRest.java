package com.visuallogictool.application.nodes.baseclassimpl;

import java.util.ArrayList;
import java.util.HashMap;

import com.visuallogictool.application.messages.flow.NextActorReceived;
import com.visuallogictool.application.messages.flow.NextActors;
import com.visuallogictool.application.messages.message.HttpRequestReceived;
import com.visuallogictool.application.messages.message.MessageNode;
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
	
	@Override
	public void preStart() throws Exception {
		// TODO Auto-generated method stub
		super.preStart();
		System.out.println("ID : " + this.id);
	}
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
	public void processMessage(String message) {
		System.out.println("RECEIVED IN API REST");
		
		
		HashMap<String, Object> context = new HashMap<String, Object>();
		
		context.put("InputSender", this.getSender());
		context.put("context", "From ApiREst");
		MessageNode messageToSend = new MessageNode(context);
	
	
		this.listNextActors.forEach(actor -> {
			actor.tell(messageToSend, ActorRef.noSender());
		});		
	}
	@Override
	public void processMessage(HashMap<String, Object> context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getGUI() {
		
	}

	

	
	@Override
	public void createMessageTrigger() {
		System.out.println("In create message trigger");
		this.context().actorSelection("/user/restRouter").tell(new RegisterRestRouter(this.api, this.getSelf()), ActorRef.noSender());
		//Server.addRoute(this.api, this.getSelf());
		
	}

	





}
