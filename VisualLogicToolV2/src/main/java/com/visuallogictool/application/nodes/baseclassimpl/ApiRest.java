package com.visuallogictool.application.nodes.baseclassimpl;

import java.util.ArrayList;
import java.util.HashMap;

import com.visuallogictool.application.messages.message.MessageNode;
import com.visuallogictool.application.messages.message.RegisterRestRouter;
import com.visuallogictool.application.messages.message.UnregisterRouter;
import com.visuallogictool.application.nodes.baseclass.InputNode;
import com.visuallogictool.application.nodes.information.Field;
import com.visuallogictool.application.nodes.information.NodeInformations;
import com.visuallogictool.application.nodes.information.NodeInformationsSetUp;
import com.visuallogictool.application.nodes.information.concrete.TextboxField;

import akka.actor.ActorRef;
import akka.actor.Props;



public class ApiRest extends InputNode {
	
	 
	private String api;

	

	public ApiRest(String id, String logId , ApiRestConfiguration apiRestConfiguration) {
		super(id, logId );
		
		this.api = apiRestConfiguration.getApi();
		
		this.shortName = "AR";
		this.logName = this.shortName + "-" + logId;
		//"/application"
		
	}

	

	@Override
	public void processMessage(HashMap<String, Object> context) {
		
		
		context.put("InputSender", this.getSender());
		context.put("context", "From ApiRest");
		MessageNode messageToSend = new MessageNode(context);
	
	
		this.listNextActors.forEach(output -> {
			output.forEach(actor -> {
				actor.tell(messageToSend, ActorRef.noSender());
			});
			
		});		
		
	}
	
	public static NodeInformations getGUI() {
		
		NodeInformationsSetUp informations = getBaseInformation();
		informations = informations.setHeader("ApiInput", "Creates an api", "Creates an Api with the given parameter").
									setFields(new Field("api", "String", "name for new api", "the name for the new api"));
		
		informations = informations.setFieldBase(new TextboxField(null, "api", "api", false, 1, null));
		
		
		informations = informations.setClass("com.visuallogictool.application.nodes.baseclassimpl.ApiRestConfiguration"
											,"com.visuallogictool.application.nodes.baseclassimpl.ApiRest");
		
		informations = informations.setShortName("AR");
		
		informations = informations.setImageUrl("https://img.icons8.com/material-outlined/24/000000/upload-to-cloud.png");
		
		return informations.getNodeInformations();
	}
	

	

	
	@Override
	public void createMessageTrigger() {
		this.context().actorSelection("/user/restRouter").tell(new RegisterRestRouter(this.api, this.getSelf()), ActorRef.noSender());
		
	}

	
	@Override
	public void postStop() throws Exception {
		super.postStop();
		this.context().actorSelection("/user/restRouter").tell(new UnregisterRouter(this.api), ActorRef.noSender());
		
		
		
	}




}
