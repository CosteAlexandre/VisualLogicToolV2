package com.visuallogictool.application.server.route;

import java.util.concurrent.CompletableFuture;

import com.visuallogictool.application.messages.message.HttpRequestReceived;
import com.visuallogictool.application.utils.JsonParser;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.http.javadsl.model.HttpEntity.Strict;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.RequestEntity;
import akka.http.javadsl.unmarshalling.Unmarshaller;
import akka.http.scaladsl.model.headers.RawHeader;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;

public abstract class Route extends AbstractActor{
	
	final Materializer materializer = ActorMaterializer.create(super.context().system());
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
		
		//RequestEntity test = httpRequest.getRequest().entity();
		
		StringBuilder s1 = new StringBuilder();
		
		
		CompletableFuture future = Unmarshaller.entityToByteArray().unmarshal(httpRequest.getRequest().entity(), materializer).thenAccept(s ->
        {
        	s1.append(new String(s));

        }).toCompletableFuture();


        future.join();
		
        
		RequestEntity entity = httpRequest.getRequest().entity();
		String body = s1.toString();
		return body;
	}
	
}
