package com.visuallogictool.application.supervision;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;

import com.visuallogictool.application.jsonclass.Flow;
import com.visuallogictool.application.server.RestServer;
import com.visuallogictool.application.utils.Files;
import com.visuallogictool.application.utils.JsonParser;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class Director extends AbstractActor{

	
	
	private RestServer server;
	private int mode;
	private Files filesUtils;
	private JsonParser jsonParser;
	
	private HashSet<ActorRef> listSupervisor;
	
	
	
	public Director(RestServer server, int mode) {
		
		this.server = server;
		this.mode = mode;
		
		this.jsonParser = new JsonParser();
		this.filesUtils = new Files();
		
		this.listSupervisor = new HashSet<ActorRef>();
	
	}
	
	@Override
	public void preStart() throws Exception {
		// TODO Add logic flow route when this actor is created, then start adding new route
		super.preStart();
		System.out.println("Director created, charging files");
		chargeFiles();
		
	}
	
	
	
	private void chargeFiles() {
		Collection<File> listFiles = filesUtils.getAllJSONFile("src/main/resources/jsonFlow");
		for (File file : listFiles) {
			
			Flow flow = jsonParser.jsonFlowConverter(file);
			System.out.println("Flow sucessfully parsed to json");
			initializeFlow(flow);
		}
		System.out.println("File charged");
	}

	
	private void initializeFlow(Flow flow) {
		System.out.println("Initializing flow");
		
		this.listSupervisor.add(this.getContext().actorOf(Supervisor.props(flow)));		
	}

	@Override
	public Receive createReceive() {
		// TODO Auto-generated method stub
		return receiveBuilder().matchAny(apply -> {
			System.out.println("Receive any in director");
		}).build();
	}

}
