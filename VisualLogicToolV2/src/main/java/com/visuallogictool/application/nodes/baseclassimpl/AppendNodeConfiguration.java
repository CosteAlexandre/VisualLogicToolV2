package com.visuallogictool.application.nodes.baseclassimpl;

import com.visuallogictool.application.jsonclass.NodeConfiguration;

public class AppendNodeConfiguration extends NodeConfiguration {


	private String var;
	private String value;
	private String newVariable;
	
	public AppendNodeConfiguration() {
		// TODO Auto-generated constructor stub
	}


	public AppendNodeConfiguration( String var, String value) {
		super();

		this.var = var;
		this.value = value;
	}

	
	public String getNewVariable() {
		return newVariable;
	}
	public void setNewVariable(String newVariable) {
		this.newVariable = newVariable;
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
