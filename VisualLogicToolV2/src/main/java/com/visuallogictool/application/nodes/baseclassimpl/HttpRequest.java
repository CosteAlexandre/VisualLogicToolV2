package com.visuallogictool.application.nodes.baseclassimpl;

import java.util.HashMap;
import java.util.concurrent.Future;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.visuallogictool.application.messages.message.MessageNode;
import com.visuallogictool.application.nodes.BaseNode;
import com.visuallogictool.application.nodes.information.Field;
import com.visuallogictool.application.nodes.information.NodeInformations;
import com.visuallogictool.application.nodes.information.NodeInformationsSetUp;
import com.visuallogictool.application.nodes.information.concrete.TextboxField;
import com.visuallogictool.application.utils.JsonParser;

import akka.actor.ActorRef;

public class HttpRequest extends BaseNode{

	private String url;
	private String var;
	
	HttpRequestConfiguration configuration;
	
	public HttpRequest(String id, HttpRequestConfiguration configuration) {
		super(id);
		this.configuration = configuration;
		this.var = configuration.getVar();
		this.url = configuration.getUrl();
	}

	@Override
	public void processMessage(HashMap<String, Object> context) {
		log.info("PROCESSING HTTP REQUEST");
		Future<HttpResponse<JsonNode>> future = Unirest.post(this.url)
				  .header("accept", "application/json")
				  .asJsonAsync(new Callback<JsonNode>() {

				    public void failed(UnirestException e) {
				        log.info("The request has failed");
				    }

				    public void completed(HttpResponse<JsonNode> response) {
				         int code = response.getStatus();
				         String message =  response.getBody().toString();
				         
				         JsonParser parser = new JsonParser(); 
				         log.info("Response received from {} : {}" ,url,message);
				         context.put(var, parser.getJsonNode(message));
				         /*JsonNode body = response.getBody();
				         InputStream rawBody = response.getRawBody();
				         http://unirest.io/java.html
				         */
				         
				         
				         getOutput().forEach(output -> {
				 			output.forEach(actor -> {
				 				actor.tell(new MessageNode(context), ActorRef.noSender());
				 			});
				 			
				 		});	
				         
				    }

				    public void cancelled() {
				        log.info("The request has been cancelled");
				        
				    }

				});	
	}


	
	public static NodeInformations getGUI() {
		
		NodeInformationsSetUp informations = getBaseInformation();
		informations = informations.setHeader("HttpRequest", "Fetch a http request", "Fetch the json in the given url").
									setFields(new Field("var", "String", "variable", "name of the variable where the information will be stored")).
									setFields(new Field("url", "String", "name of url", "the name of the url that will be used"));
		
		informations = informations.setFieldBase(new TextboxField(null, "var", "var", false, 1, null)).
									setFieldBase(new TextboxField(null, "url", "url", false, 2, null));
		
		informations = informations.setClass("com.visuallogictool.application.nodes.baseclassimpl.HttpRequestConfiguration"
				,"com.visuallogictool.application.nodes.baseclassimpl.HttpRequest");
		
		informations = informations.setShortName("HR");
		
		informations = informations.setImageUrl("https://img.icons8.com/ios/50/000000/domain.png");
		
		return informations.getNodeInformations();
	}

}
