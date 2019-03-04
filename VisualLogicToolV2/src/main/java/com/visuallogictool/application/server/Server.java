package com.visuallogictool.application.server;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

import akka.NotUsed;
import akka.Done;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.pattern.Patterns;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

import static akka.http.javadsl.server.Directives.*;

public class Server {

	public static ActorSystem system;
	private static Http http;
	private static ActorMaterializer materializer;
	private static CompletionStage<ServerBinding> binding;
	public static Flow<HttpRequest, HttpResponse, NotUsed> routeFlow;
	
	private static int port;
	private static ConnectHttp host;
	
	public Server(ActorSystem system, int port) {
		this.system = system;
		this.port = port;
		
		
	}
	
	
	public void startServer() {
		
		this.http = Http.get(system);
		this.materializer = ActorMaterializer.create(system);
		
		host = ConnectHttp.toHost("localhost", port);
		 routeFlow = createRoute("logicFlow").flow(system, materializer);
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

	  private Route createRoute(String api) {
		    return  concat(
		        path(api, () ->
		            get(() ->
		                complete("<h1>In logic fLow  route</h1>"))
		            
		        		),
		        path(api, () ->
	            post(() ->
	                complete("<h1>In logic fLow  route</h1>"))
	            
	        		));
	  }



	  
	
}
