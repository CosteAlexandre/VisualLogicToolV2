package com.visuallogictool.application.server;

import java.util.concurrent.CompletionStage;

import com.visuallogictool.application.messages.message.HttpRequestReceived;

import akka.NotUsed;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.marshalling.Marshaller;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.Route;
import akka.pattern.Patterns;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

import static akka.http.javadsl.server.Directives.*;
import static scala.compat.java8.FutureConverters.toJava;
public class RestServer {

	private ActorSystem system;
	private Http http;
	private ActorMaterializer materializer;
	private CompletionStage<ServerBinding> binding;
	private Flow<HttpRequest, HttpResponse, NotUsed> routeFlow;
	
	private ActorSelection restRouter;
	
	private long time = 10000000;
	
	private int port;
	private ConnectHttp host;
	
	public RestServer(ActorSystem system, int port) {
		this.system = system;
		this.port = port;
		
		this.restRouter = this.system.actorSelection("/user/restRouter");
	}
	
	
	public void startServer() {
		
		this.http = Http.get(system);
		this.materializer = ActorMaterializer.create(system);
		
		host = ConnectHttp.toHost("localhost", port);
		 routeFlow = createRoute().flow(system, materializer);
		    binding = http.bindAndHandle(routeFlow,
		        host, materializer);
		    
		    System.out.println("Server online at http://localhost:"+port);

		    
	}

	
	public void stopServer() {
		 binding
	        .thenCompose(ServerBinding::unbind) // trigger unbinding from the port
	        .thenAccept(unbound -> system.terminate()); // and shutdown when done
	}

	  private Route createRoute() {
		  
		    return  concat(
		        pathPrefix("", () ->
		            get(() -> {
		            	return complete("<h1>In logic fLow  route</h1>");
		            }       
		            
		        		)),
		        pathPrefix("", () ->
	            post(() ->{
	            	System.out.println("Sending message");
	            	CompletionStage<HttpResponse> future = toJava(( Patterns.ask(restRouter, new HttpRequestReceived("coucou","Hola"), time))).thenApply(r -> {
	            		System.out.println("Response received : " + r.toString());
	            		return (HttpResponse) r;
	            	});
	            	
	            	return complete("<h1>In logic fLow  route</h1>");
	            			
	            	})));
		        
		       /* post(()->{
		        	
		        	System.out.println(Marshaller.byteStringToEntity().toString());
		        	return complete("ok");
		        })));*/
		 /*       
		    post(() ->entity(Marshaller.entityToResponse, (content) -> {
		    	ObjectMapper objectMapper = new ObjectMapper();
		    	
		    	try {
					Request requestBody = objectMapper.readValue(content, Request.class);
					System.out.println(requestBody.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	
        	    System.out.println("request body: " + content);
        	    return complete("ok");
        	  }))));*/
		    
	  }



	  
	
}
