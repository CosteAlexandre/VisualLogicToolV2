package com.visuallogictool.application.server;

import java.util.HashMap;

import com.visuallogictool.application.messages.message.HttpRequestReceived;
import com.visuallogictool.application.messages.message.RegisterComplete;
import com.visuallogictool.application.messages.message.RegisterFailed;
import com.visuallogictool.application.messages.message.RegisterRestRouter;
import com.visuallogictool.application.messages.message.UnregisterRouter;
import com.visuallogictool.application.server.route.AddFlowGraphRoute;
import com.visuallogictool.application.server.route.DeleteFlowRoute;
import com.visuallogictool.application.server.route.GetAllFlowRoute;
import com.visuallogictool.application.server.route.GetFlowGraphRoute;
import com.visuallogictool.application.server.route.GetNodesInformationsRoute;
import com.visuallogictool.application.server.route.LogicFlowRoute;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.http.javadsl.model.HttpResponse;
import akka.http.scaladsl.model.headers.RawHeader;

public class RestRouter extends AbstractActor{

	private HashMap<String, ActorRef> api;
	
	public static Props props() {
	    return Props.create(RestRouter.class, () -> new RestRouter());
	}
	
	
	public RestRouter() {
		this.api = new HashMap<String, ActorRef>();		
	}
	
	@Override
	public void preStart() throws Exception {
		super.preStart();
		register();
	}
	
	private void register() {
		ActorRef deleteFlow =  this.getContext().actorOf(DeleteFlowRoute.props());
		ActorRef addFlowGraph =  this.getContext().actorOf(AddFlowGraphRoute.props());
		ActorRef logicFlow =  this.getContext().actorOf(LogicFlowRoute.props());
		ActorRef getNodesInformations =  this.getContext().actorOf(GetNodesInformationsRoute.props());
		ActorRef getAllFlow =  this.getContext().actorOf(GetAllFlowRoute.props());
		ActorRef getFlowGraph =  this.getContext().actorOf(GetFlowGraphRoute.props());
		api.put("/deleteFlow", deleteFlow);
		api.put("/addFlowGraph", addFlowGraph);
		api.put("/logicFlow", logicFlow);
		api.put("/getNodesInformations", getNodesInformations);
		api.put("/getAllFlow", getAllFlow);
		api.put("/getFlowGraph", getFlowGraph);
	}
	
	
	@Override
	public Receive createReceive() {
		return receiveBuilder().match(HttpRequestReceived.class, request -> {
			String url = request.getRequest().getUri().getPathString();
			
			if(this.api.containsKey(url)) {
				this.api.get(url).tell(new HttpRequestReceived(request.getRequest()), this.getSender());
			}else {
				HttpResponse response = HttpResponse.create()
						.withStatus(404)
						.withEntity("Api not found").addHeader(new RawHeader("Access-Control-Allow-Origin","*" ));
				this.getSender().tell(response, ActorRef.noSender());
			}
			
		}).match(RegisterRestRouter.class, message -> {
			if(this.api.containsKey(message.getApi())) {
				message.getActor().tell(new RegisterFailed(), ActorRef.noSender());
			} else {
				this.api.put(message.getApi(), message.getActor());
				message.getActor().tell(new RegisterComplete(), ActorRef.noSender());
			}
			
		}).match(UnregisterRouter.class, apply ->{			
			this.api.remove(apply.getApi());
		}).matchAny(apply->{
			
			
		}).build();
		
		
	}

}
