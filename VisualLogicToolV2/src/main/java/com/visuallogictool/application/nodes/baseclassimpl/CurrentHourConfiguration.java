package com.visuallogictool.application.nodes.baseclassimpl;

import com.visuallogictool.application.jsonclass.NodeConfiguration;

public class CurrentHourConfiguration extends NodeConfiguration{

	private String var;

	public CurrentHourConfiguration() {
		// TODO Auto-generated constructor stub
	}
	
	public CurrentHourConfiguration(String var) {
		super();
		this.var = var;
	}

	public String getVar() {
		return var;
	}
	
	
	
}
