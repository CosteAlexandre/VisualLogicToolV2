package com.visuallogictool.application.server;

import static scala.compat.java8.FutureConverters.toJava;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionStage;

import com.visuallogictool.application.messages.message.HttpRequestReceived;

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.dispatch.ExecutionContexts;
import akka.dispatch.forkjoin.ForkJoinPool;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.IncomingConnection;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.japi.function.Function;
import akka.pattern.Patterns;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import scala.concurrent.ExecutionContext;

public class RestServer {

	
	
	private ActorSystem system;
	private ActorMaterializer materializer;
	private ExecutionContext ec;
	
	private LoggingAdapter log;
	
	private ActorSelection restRouter;
	
	private long timeout = 10000000;
	
	private int port;
	
	public RestServer(ActorSystem system, int port) {
		this.system = system;
		this.port = port;
		log = Logging.getLogger(system, this);
		this.restRouter = this.system.actorSelection("/user/restRouter");
		/*
		Map<String, Object> mdc;
        mdc = new HashMap<String, Object>();
        mdc.put("group", "Director");
        
        log.setMDC(mdc);*/
	}
	
	
	public void startServer() {
		
		
		this.materializer = ActorMaterializer.create(system);
		ec = ExecutionContexts.fromExecutor(ForkJoinPool.commonPool());
		
		Source<IncomingConnection, CompletionStage<ServerBinding>> serverSource
        = Http.get(system).
        bind(ConnectHttp.toHost("localhost",port), materializer);
		
		    
		log.info("Server online at http://localhost:"+port);

		CompletionStage<ServerBinding> serverBindingFuture
        = serverSource
            
            .to(Sink.foreach(conn ->   
                 {
                   log.debug("Accepted new connection from '{}' ..." , conn.remoteAddress());
                   conn.handleWithAsyncHandler(handler, materializer);
                 }
             ))
             .run(materializer);   
		
		
		serverBindingFuture.whenCompleteAsync((binding, failure) -> {			   
		    if (failure!= null) {
		        
		        //WE GOT AN BINDING ERROR
		        log.error("HTTP REST Binding Error...");		       
		    }
		    
		}, system.dispatcher());
	}

	Function<HttpRequest, CompletionStage<HttpResponse>> handler = req -> { 
		log.info("Process Incoming HTTP Request -> {}",req);
		CompletionStage<HttpResponse> futureDirective = toJava(Patterns.ask(this.restRouter, new HttpRequestReceived(req), this.timeout))
		        .thenApply (r ->{ 
		            log.info("=====> {}",r);
		            return  (HttpResponse) r;});
		return futureDirective;
	};




	  
	
}
