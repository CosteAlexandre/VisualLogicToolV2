package com.visuallogictool.application.main;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.visuallogictool.application.configuration.Configuration;
import com.visuallogictool.application.jsonclass.Flow;
import com.visuallogictool.application.server.RestRouter;
import com.visuallogictool.application.server.RestServer;
import com.visuallogictool.application.supervision.Director;
import com.visuallogictool.application.supervision.Supervisor;
import com.visuallogictool.application.utils.Files;
import com.visuallogictool.application.utils.JsonParser;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class MainApplication {

	
	
	
	public static void main(String[] args) {
		
		String configurationPath = "src/main/resources/configuration.conf";
		
		JsonParser jsonParser = new JsonParser();
		
		Configuration configuration = jsonParser.getConfigurationFile(configurationPath);
		
		System.out.println("port : " +configuration.getPort() + " mode : " + configuration.getMode());
		
		ActorSystem system = ActorSystem.create("system");
		
		
		RestServer server = new RestServer(system, configuration.getPort());
		
		server.startServer();
		
		system.actorOf(RestRouter.props(),"restRouter");
		
		system.actorOf(Props.create(Director.class, server, configuration.getMode()),"director");
		
		
	}

		
		

	
	
	
	
	
}
