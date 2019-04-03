package com.visuallogictool.application.server.route;

import com.visuallogictool.application.messages.flow.GetFlowGraph;
import com.visuallogictool.application.messages.message.HttpRequestReceived;

import akka.actor.Props;

public class GetFlowGraphRoute extends Route{

	public static Props props() {
	    return Props.create(GetFlowGraphRoute.class, () -> new GetFlowGraphRoute());
	}

	
	public GetFlowGraphRoute() {
		super();
	}
	
	@Override
	public Receive createReceive() {
		// TODO Auto-generated method stub
		return receiveBuilder().match(HttpRequestReceived.class, apply -> {
			String body = this.getBody(apply);
			this.getContext().getSystem().actorSelection("/user/director").tell(new GetFlowGraph(body), this.getSender());;
//			getAllJSONFile(String path)
		 /*
			HttpResponse response = HttpResponse.create()
					.withStatus(200)
					.withEntity(jsonParse.getJson("")).addHeader(new RawHeader("Access-Control-Allow-Origin","*" ));
			//apply.getRequest().getHeader("Origin").get().value() 
			this.getSender().tell(response, ActorRef.noSender());
			
			*/
		}).build();
		
	}
	
}
