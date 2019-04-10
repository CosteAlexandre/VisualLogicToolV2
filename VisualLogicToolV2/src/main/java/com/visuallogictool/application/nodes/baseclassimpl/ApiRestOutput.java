package com.visuallogictool.application.nodes.baseclassimpl;

import java.util.HashMap;

import com.visuallogictool.application.nodes.baseclass.OutputNode;
import com.visuallogictool.application.nodes.information.Field;
import com.visuallogictool.application.nodes.information.NodeInformations;
import com.visuallogictool.application.nodes.information.NodeInformationsSetUp;
import com.visuallogictool.application.nodes.information.concrete.TextboxField;
import com.visuallogictool.application.utils.JsonParser;

import akka.actor.ActorRef;
import akka.http.javadsl.model.HttpResponse;
import akka.http.scaladsl.model.headers.RawHeader;

public class ApiRestOutput extends OutputNode{

	private String htm;
	private String var;
	
	private ApiRestOutputConfiguration configuration;
	private JsonParser jsonParser;
	public ApiRestOutput(String id, String logId , String flowId,ApiRestOutputConfiguration configuration) {
		super(id, logId, flowId);
		
		this.jsonParser = new JsonParser();
		
		this.shortName = "ARO";
		this.setLogName(flowId, logId);
		this.configuration = configuration;
		
		this.htm = this.configuration.getHtm();
		this.var = this.configuration.getVar();
	}

	@Override
	public void createMessageResponse(HashMap<String, Object> context) {
		ActorRef actor = ((ActorRef)context.get("InputSender"));
		
		String messageResp;
		if(this.var.equals("")) {
			log.info("No var put using htm");
			messageResp = this.htm;
		}else {
			messageResp = (String) context.get(this.var);
			
			log.info("using var : " + messageResp);
		}
		log.info("sending message back");
		HttpResponse response = HttpResponse.create()
				.withStatus(200)
				.withEntity(jsonParser.getJson(messageResp)).addHeader(new RawHeader("Access-Control-Allow-Origin","*" ));
		actor.tell(response, ActorRef.noSender());
	}

	@Override
	public void processMessage(HashMap<String, Object> context) {
		createMessageResponse(context);
		
	}
	
	public static NodeInformations getGUI() {
		
		NodeInformationsSetUp informations = getBaseInformation();
		informations = informations.setHeader("ApiOutput", "send back a response", "Sends back a response to the api called if no parameter var given htm will be used").
										setFields(new Field("htm", "String", "print the text given in parameter", "print the text given in parameter")).
										setFields(new Field("var", "String", "take the informations of the var", "take the informations of the var"));
		
		informations = informations.setFieldBase(new TextboxField(null, "htm", "htm", false, 1, null));
		informations = informations.setFieldBase(new TextboxField(null, "var", "var", false, 1, null));
		
		informations = informations.setClass("com.visuallogictool.application.nodes.baseclassimpl.ApiRestOutputConfiguration"
				,"com.visuallogictool.application.nodes.baseclassimpl.ApiRestOutput");
		
		informations = informations.setShortName("ARO");
		
		informations = informations.setImageUrl("https://img.icons8.com/material-outlined/24/000000/download-from-cloud.png");
		
		return informations.getNodeInformations();
	}


}
