package com.visuallogictool.application.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;

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
	public Flow jsonFlowConverter(String json) {
		
		try {
			Flow flow = objectMapper.readValue(json, Flow.class);
			return flow;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//error
		return null;
	}
	public Flow jsonFlowConverter(File file) {
		try {
			
			JsonNode jsonNode = objectMapper.readTree(file);



			Flow flow = objectMapper.readValue(file, Flow.class);
			
			System.out.println(jsonNode.toString());
			
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
	public JsonNode getJsonNode(String json) {
		JsonNode jsonNode;
		try {
			jsonNode = objectMapper.readTree(json);
			return jsonNode;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	public void addFlowJsonFile(Flow flow, String path) {
        
		try {
			objectMapper.writeValue(new File(path), flow);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
