package com.visuallogictool.application.utils;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.visuallogictool.application.configuration.Configuration;

public class JsonParser {

	private static ObjectMapper objectMapper = new ObjectMapper();
	
	
	public static Configuration getConfigurationFile(String configurationPath) {
		
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

	
	
	
}
