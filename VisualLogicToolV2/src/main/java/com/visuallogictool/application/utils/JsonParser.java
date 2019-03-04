package com.visuallogictool.application.utils;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.visuallogictool.application.configuration.Configuration;
import com.visuallogictool.application.jsonclass.Flow;

public class JsonParser {

	private ObjectMapper objectMapper = new ObjectMapper();
	
	
	public Configuration getConfigurationFile(String configurationPath) {
		
		File file = new File(configurationPath);
		
		try {
			
			Configuration configuration = objectMapper.readValue(file, Configuration.class);
			
			return configuration;
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}

	public Flow jsonFlowConverter(File file) {
		try {
			
			JsonNode jsonNode = objectMapper.readTree(file);

			if(jsonNode.get("CreateFlow") == null) {
				System.out.println("Error");
			}
			
			JsonNode node = jsonNode.get("CreateFlow");

			Flow flow = objectMapper.treeToValue(node, Flow.class);
			
			System.out.println(node.toString());
			
			return flow;
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
}
