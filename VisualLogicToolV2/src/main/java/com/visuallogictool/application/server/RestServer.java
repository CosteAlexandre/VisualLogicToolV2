package com.visuallogictool.application.server;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

import com.visuallogictool.application.messages.message.HttpRequestReceived;

import akka.NotUsed;
import akka.Done;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.http.javadsl.unmarshalling.Unmarshaller;
import akka.pattern.Patterns;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

import static akka.http.javadsl.server.Directives.*;
import static scala.compat.java8.FutureConverters.toJava;
public class RestServer {

	private static ActorSystem system;
	private static Http http;
	private static ActorMaterializer materializer;
	private static CompletionStage<ServerBinding> binding;
	private static Flow<HttpRequest, HttpResponse, NotUsed> routeFlow;
	
	private static ActorSelection restRouter;
	
	private static long time = 10000000;
	
	private static int port;
	private static ConnectHttp host;
	
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

		    
		    
		   //system.actorOf(ApiRest.props(1, "test"));
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
	            		return (HttpResponse) r;
	            	});
	            	System.out.println(Unmarshaller.entityToString());
	            	/*entity(Unmarshaller.entityToString(), (string) -> {
	            	    System.out.println("request body: " + string);
	            	    return complete("ok");
	            	  });*/
	            	
	            	return complete("<h1>In logic fLow  route</h1>");
	            			
	            	})));
		    
	  }



	  
	
}
