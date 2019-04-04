package com.visuallogictool.application.nodes.baseclassimpl;

import com.visuallogictool.application.jsonclass.NodeConfiguration;

public class ReplaceWithNodeConfiguration extends NodeConfiguration {


	private String var;
	private String value;
	private String newValue;
	private String otherVar;
	
	public ReplaceWithNodeConfiguration() {
		// TODO Auto-generated constructor stub
	}


	public ReplaceWithNodeConfiguration( String var, String value, String newValue, String otherVar) {
		super();

		this.var = var;
		this.value = value;
		this.newValue = newValue;
		this.otherVar = otherVar;
	}

	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	public String getOtherVar() {
		return otherVar;
	}
	public void setOtherVar(String otherVar) {
		this.otherVar = otherVar;
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
