package com.visuallogictool.application.server.route;

import com.visuallogictool.application.messages.message.HttpRequestReceived;
import com.visuallogictool.application.utils.JsonParser;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.RequestEntity;
import akka.http.javadsl.model.HttpEntity.Strict;
import akka.http.scaladsl.model.headers.RawHeader;

public abstract class Route extends AbstractActor{
	
	
	protected JsonParser jsonParser;
	
	public Route() {
		jsonParser = new JsonParser();
	}
	
	protected void sendResponse(Object object) {
		HttpResponse response = HttpResponse.create()
				.withStatus(200)
				.withEntity(jsonParser.getJson(object)).addHeader(new RawHeader("Access-Control-Allow-Origin","*" ));
		
		
		
		this.getSender().tell(response, ActorRef.noSender());
		
	}
	
	protected String getBody(HttpRequestReceived httpRequest) {
		
		RequestEntity test = httpRequest.getRequest().entity();
		Strict entity = (Strict) httpRequest.getRequest().entity();
		String body = entity.getData().decodeString("UTF-8");
		return body;
	}
	
}
