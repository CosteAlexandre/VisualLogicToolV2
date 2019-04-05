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
import com.visuallogictool.application.server.route.GetFlowLogRoute;
import com.visuallogictool.application.server.route.GetNodesInformationsRoute;
import com.visuallogictool.application.server.route.LogicFlowRoute;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.http.javadsl.model.HttpResponse;
import akka.http.scaladsl.model.headers.RawHeader;

public class RestRouter extends AbstractActor{

	private HashMap<String, ActorRef> api;
	
	private LoggingAdapter log;
	private ActorSystem system;
	
	public static Props props(ActorSystem system) {
	    return Props.create(RestRouter.class, () -> new RestRouter(system));
	}
	
	
	public RestRouter(ActorSystem system) {
		this.api = new HashMap<String, ActorRef>();	
		this.system = system;
		log = Logging.getLogger(this.system, this);
	}
	
	@Override
	public void preStart() throws Exception {
		super.preStart();
		log.info("Starting RestRouter");
		register();
	}
	
	private void register() {
		log.info("Registering api");
		ActorRef deleteFlow =  this.getContext().actorOf(DeleteFlowRoute.props());
		ActorRef addFlowGraph =  this.getContext().actorOf(AddFlowGraphRoute.props());
		ActorRef logicFlow =  this.getContext().actorOf(LogicFlowRoute.props());
		ActorRef getNodesInformations =  this.getContext().actorOf(GetNodesInformationsRoute.props());
		ActorRef getAllFlow =  this.getContext().actorOf(GetAllFlowRoute.props());
		ActorRef getFlowGraph =  this.getContext().actorOf(GetFlowGraphRoute.props());
		ActorRef getFlowLog =  this.getContext().actorOf(GetFlowLogRoute.props());
		api.put("/deleteFlow", deleteFlow);
		api.put("/addFlowGraph", addFlowGraph);
		api.put("/logicFlow", logicFlow);
		api.put("/getNodesInformations", getNodesInformations);
		api.put("/getAllFlow", getAllFlow);
		api.put("/getFlowGraph", getFlowGraph);
		api.put("/getFlowLog", getFlowLog);
	}
	
	
	@Override
	public Receive createReceive() {
		return receiveBuilder().match(HttpRequestReceived.class, request -> {
			String url = request.getRequest().getUri().getPathString();
			
			if(this.api.containsKey(url)) {
				log.info("Contains url {} transferring message tp {}", url , this.api.get(url));
				this.api.get(url).tell(new HttpRequestReceived(request.getRequest()), this.getSender());
			}else {
				log.info("Wrong url given : {}",url);
				HttpResponse response = HttpResponse.create()
						.withStatus(404)
						.withEntity("Api not found").addHeader(new RawHeader("Access-Control-Allow-Origin","*" ));
				this.getSender().tell(response, ActorRef.noSender());
			}
			
		}).match(RegisterRestRouter.class, message -> {
			if(this.api.containsKey(message.getApi())) {
				log.info("Failed to register api {} already exists", message.getApi());
				message.getActor().tell(new RegisterFailed(), ActorRef.noSender());
			} else {
				this.api.put(message.getApi(), message.getActor());
				message.getActor().tell(new RegisterComplete(), ActorRef.noSender());
				log.info("Registering api {} to actor {}", message.getApi(), message.getActor());
			}
			
		}).match(UnregisterRouter.class, apply ->{	
			log.info("Unregister api {}", apply.getApi());
			this.api.remove(apply.getApi());
		}).matchAny(apply->{
			
			
		}).build();
		
		
	}

}
