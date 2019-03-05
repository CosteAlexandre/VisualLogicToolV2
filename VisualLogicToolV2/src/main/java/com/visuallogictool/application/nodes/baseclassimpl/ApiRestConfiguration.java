package com.visuallogictool.application.nodes.baseclassimpl;

import com.visuallogictool.application.jsonclass.NodeConfiguration;

public class ApiRestConfiguration extends NodeConfiguration {


	private String api;
	
	public ApiRestConfiguration() {
		// TODO Auto-generated constructor stub
	}


	public ApiRestConfiguration( String api) {
		super();

		this.api = api;
	}




	public String getApi() {
		return api;
	}


	public void setApi(String api) {
		this.api = api;
	}
	
	
	
	
}
