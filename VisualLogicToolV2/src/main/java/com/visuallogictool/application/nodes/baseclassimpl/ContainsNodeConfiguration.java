package com.visuallogictool.application.nodes.baseclassimpl;

import com.visuallogictool.application.jsonclass.NodeConfiguration;

public class ContainsNodeConfiguration extends NodeConfiguration {


	private String var;
	private String value;
	
	public ContainsNodeConfiguration() {
		// TODO Auto-generated constructor stub
	}


	public ContainsNodeConfiguration( String var, String value) {
		super();

		this.var = var;
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getVar() {
		return var;
	}
	public void setVar(String var) {
		this.var = var;
	}


	
	
}
