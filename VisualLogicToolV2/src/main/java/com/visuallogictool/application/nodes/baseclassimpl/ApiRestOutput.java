package com.visuallogictool.application.nodes.baseclassimpl;

import java.util.HashMap;

import com.visuallogictool.application.nodes.baseclass.OutputNode;

import akka.actor.ActorRef;
import akka.http.javadsl.model.HttpResponse;

public class ApiRestOutput extends OutputNode{

	private String htm;
	private ApiRestOutputConfiguration configuration;
	
	public ApiRestOutput(int id,ApiRestOutputConfiguration configuration) {
		super(id);
		
		this.configuration = configuration;
		this.htm = this.configuration.getHtm();
	}

	@Override
	public void createMessageResponse(HashMap<String, Object> context) {
		ActorRef actor = ((ActorRef)context.get("InputSender"));
		
		String message;
		if(context.containsKey("output")) {
			message = (String) context.get("output");
		} else {
			message = "message";
		}
		
		HttpResponse response = HttpResponse.create()
				.withStatus(200)
				.withEntity(this.htm);
		actor.tell(response, ActorRef.noSender());
	}

	@Override
	public void processMessage(HashMap<String, Object> context) {
		System.out.println("RECEIVED IN OUTPUT NODE");
		createMessageResponse(context);
		
	}
	@Override
	public void getGUI() {
		// TODO Auto-generated method stub
		
	}


}
