package com.visuallogictool.application.nodes.baseclassimpl;

import java.util.HashMap;

import com.visuallogictool.application.nodes.baseclass.OutputNode;
import com.visuallogictool.application.nodes.information.Field;
import com.visuallogictool.application.nodes.information.NodeInformations;
import com.visuallogictool.application.nodes.information.NodeInformationsSetUp;
import com.visuallogictool.application.nodes.information.concrete.TextboxField;

import akka.actor.ActorRef;
import akka.http.javadsl.model.HttpResponse;

public class ApiRestOutput extends OutputNode{

	private String htm;
	private ApiRestOutputConfiguration configuration;
	
	public ApiRestOutput(String id,ApiRestOutputConfiguration configuration) {
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
	
	public static NodeInformations getGUI() {
		
		NodeInformationsSetUp informations = new NodeInformationsSetUp();
		informations = informations.setHeader("ApiOutput", "send back a response", "Sends back a response to the api called");
		
		informations = informations.setType("OutputNode");

		informations = informations.setFieldBase(new TextboxField(null, "htm", "htm", true, 1, null));
		
		informations = informations.setClass("com.visuallogictool.application.nodes.baseclassimpl.ApiRestOutputConfiguration"
				,"com.visuallogictool.application.nodes.baseclassimpl.ApiRestOutput");
		
		informations = informations.setShortName("ARO");
		
		return informations.getNodeInformations();
	}


}
