package com.visuallogictool.application.nodes.baseclassimpl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.Future;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.visuallogictool.application.messages.message.MessageNode;
import com.visuallogictool.application.nodes.BaseNode;
import com.visuallogictool.application.utils.JsonParser;

import akka.actor.ActorRef;

public class HttpRequest extends BaseNode{

	private String url;
	private String var;
	
	HttpRequestConfiguration configuration;
	
	public HttpRequest(int id, HttpRequestConfiguration configuration) {
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
				         
				         getOutput().forEach( node -> {
				        	 node.tell(new MessageNode(context), ActorRef.noSender());
				         });
				         
				    }

				    public void cancelled() {
				        log.info("The request has been cancelled");
				        
				    }

				});	
	}


	@Override
	public void getGUI() {
		// TODO Auto-generated method stub
		
	}

}
