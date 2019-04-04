package com.visuallogictool.application.nodes.baseclassimpl;

import com.visuallogictool.application.jsonclass.NodeConfiguration;

public class PrependNodeConfiguration extends NodeConfiguration {


	private String var;
	private String value;
	private String newVariable;
	
	public PrependNodeConfiguration() {
		// TODO Auto-generated constructor stub
	}


	public PrependNodeConfiguration( String var, String value, String newVariable) {
		super();

		this.var = var;
		this.value = value;
		this.newVariable = newVariable;
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
