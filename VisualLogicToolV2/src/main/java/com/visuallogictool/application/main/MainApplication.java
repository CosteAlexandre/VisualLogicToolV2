package com.visuallogictool.application.main;

import com.visuallogictool.application.configuration.Configuration;
import com.visuallogictool.application.utils.JsonParser;

public class MainApplication {

	
	
	public static void main(String[] args) {
		
		String configurationPath = "src/main/resources/configuration.conf";
		
		Configuration configuration = JsonParser.getConfigurationFile(configurationPath);
		
		System.out.println("port : " +configuration.getPort() + " mode : " + configuration.getMode());
		
		
	}
	
	
	
	
}
