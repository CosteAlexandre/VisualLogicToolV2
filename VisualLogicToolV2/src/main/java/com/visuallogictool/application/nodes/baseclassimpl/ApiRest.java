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
	
	 
	 //final LoggingAdapter log = Logging.getLogger(getContext().getSystem().eventStream(), "my.string");
	  static public Props props(String id, ApiRestConfiguration apiRestConfiguration) {
		    return Props.create(ApiRest.class, () -> new ApiRest(id, apiRestConfiguration));
	  }


	private String api;
	
	@Override
	public void preStart() throws Exception {
		// TODO Auto-generated method stub
		super.preStart();
		System.out.println("ID : " + this.id);
	}
	public ApiRest(String id, ApiRestConfiguration apiRestConfiguration) {
		super(id);
		
		this.api = apiRestConfiguration.getApi();
		
		//"/application"
		
	}

	

	@Override
	public void processMessage(HashMap<String, Object> context) {
		
		System.out.println("RECEIVED IN API REST");
		
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
		System.out.println("In create message trigger");
		this.context().actorSelection("/user/restRouter").tell(new RegisterRestRouter(this.api, this.getSelf()), ActorRef.noSender());
		
	}

	
	@Override
	public void postStop() throws Exception {
		super.postStop();
		this.context().actorSelection("/user/restRouter").tell(new UnregisterRouter(this.api), ActorRef.noSender());
		
		
		
	}




}
