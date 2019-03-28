package com.visuallogictool.application.nodes.baseclassimpl;

import java.util.HashMap;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;
import com.visuallogictool.application.messages.message.MessageNode;
import com.visuallogictool.application.nodes.BaseNode;
import com.visuallogictool.application.nodes.information.Field;
import com.visuallogictool.application.nodes.information.NodeInformations;
import com.visuallogictool.application.nodes.information.NodeInformationsSetUp;
import com.visuallogictool.application.nodes.information.concrete.TextboxField;

import akka.actor.ActorRef;

public class SetJsonNode extends BaseNode{

	private SetJsonNodeConfiguration configuration;
	
	private String variable;	
	private String path;
	private String valName;
	
	public SetJsonNode(String id, SetJsonNodeConfiguration configuration) {
		super(id);
		this.configuration = configuration;
		this.variable = configuration.getVariable();
		this.path = configuration.getPath();
		this.valName = configuration.getValName();
	}

	@Override
	public void processMessage(HashMap<String, Object> context) {
		log.info("IN SET JSONNODE");
		JsonNode node = (JsonNode)context.get(this.variable);
		System.out.println();
		/*
		 * Regex the string with the dots, then do multiple path search depending on number of "dot", then how for the table // add index in the beginning?
		 */
		
		//forecast.forecastday[1]
		
		String[] split = this.path.split(Pattern.quote("."));
		String pattern = "(.*)\\[([0-9]*)\\]";
		
		for (int i = 0; i < split.length; i++) {
			System.out.println("Split : "+split[i]);
			
			if(split[i].matches(pattern)) {
				String firstPart = split[1].replaceAll(pattern, "$1");
				String index = split[1].replaceAll(pattern, "$2");
				node = node.findPath(firstPart).get(Integer.parseInt(index));
			} else {
				node = node.findPath(split[i]);
			}
			
		}
		
		log.info("Node : {}",node.toString());
		
		context.put(this.valName, node);
		
		this.getOutput().forEach( output -> {
			output.tell(new MessageNode(context), ActorRef.noSender());
		});
		
	}

	
	public static NodeInformations getGUI() {
		
		NodeInformationsSetUp informations = new NodeInformationsSetUp();
		informations = informations.setHeader("SetJson", "modify the current json", "Modifies the current json and put it in a new variable").
									setFields(new Field("variable", "String", "name of variable", "the name of the variable that contains the json node")).
									setFields(new Field("path", "String", "new path", "path to the new variable in the json node")).
									setFields(new Field("valName", "String", "name of new variable", "the name of the new varaible where the new information will be stored"));
		
		informations = informations.setFieldBase(new TextboxField(null, "variable", "variable", true, 1, null)).
									setFieldBase(new TextboxField(null, "path", "path", true, 2, null)).
									setFieldBase(new TextboxField(null, "valName", "valName", true, 3, null));
		
		informations = informations.setType("BaseNode","BaseNode");
		
		informations = informations.setClass("com.visuallogictool.application.nodes.baseclassimpl.SetJsonNodeConfiguration"
				,"com.visuallogictool.application.nodes.baseclassimpl.SetJsonNode");
		
		informations = informations.setShortName("SJN");
		
		return informations.getNodeInformations();
	}

}
