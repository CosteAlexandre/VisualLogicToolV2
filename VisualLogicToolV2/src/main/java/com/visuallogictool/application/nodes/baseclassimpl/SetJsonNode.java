package com.visuallogictool.application.nodes.baseclassimpl;

import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.visuallogictool.application.nodes.BaseNode;

public class SetJsonNode extends BaseNode{

	private SetJsonNodeConfiguration configuration;
	
	private String variable;	
	private String path;
	private String valName;
	
	public SetJsonNode(int id, SetJsonNodeConfiguration configuration) {
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
		
		log.info("Node : {}",node.findPath("forecast").findPath("forecastday").get(0).toString());
		
	}

	@Override
	public void getGUI() {
		// TODO Auto-generated method stub
		
	}

}
