package com.visuallogictool.application.server;

import java.util.HashMap;

import com.visuallogictool.application.messages.message.IncorrectMessage;
import com.visuallogictool.application.messages.message.MessageReceived;
import com.visuallogictool.application.messages.message.RegisterComplete;
import com.visuallogictool.application.messages.message.RegisterFailed;
import com.visuallogictool.application.messages.message.RegisterRestRouter;
import com.visuallogictool.application.nodes.baseclassimpl.ApiRest;
import com.visuallogictool.application.nodes.baseclassimpl.ApiRestConfiguration;
import com.visuallogictool.application.messages.message.HttpRequestReceived;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.http.javadsl.model.HttpResponse;

public class RestRouter extends AbstractActor{

	private HashMap<String, ActorRef> api;
	
	public static Props props() {
	    return Props.create(RestRouter.class, () -> new RestRouter());
	}
	
	
	public RestRouter() {
		this.api = new HashMap<String, ActorRef>();		
	}
	
	
	@Override
	public Receive createReceive() {
		return receiveBuilder().match(HttpRequestReceived.class, request -> {
			String url = request.getRequest().getUri().getPathString();
			System.out.println("Router received message from : " + url);
			
			if(this.api.containsKey(url)) {
				this.api.get(url).tell(new HttpRequestReceived(request.getRequest()), this.getSender());
			}else {
				HttpResponse response = HttpResponse.create()
						.withStatus(404)
						.withEntity("Api not found");
				this.getSender().tell(response, ActorRef.noSender());
			}
			
		}).match(RegisterRestRouter.class, message -> {
			System.out.println("Registered in : " + message.getApi());
			if(this.api.containsKey(message.getApi())) {
				message.getActor().tell(new RegisterFailed(), ActorRef.noSender());
			} else {
				this.api.put(message.getApi(), message.getActor());
				message.getActor().tell(new RegisterComplete(), ActorRef.noSender());
			}
			
		}).build();
		
	}

}
