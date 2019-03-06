package com.visuallogictool.application.nodes.baseclassimpl;

import java.util.ArrayList;

import com.visuallogictool.application.jsonclass.NodeConfiguration;

public class SetJsonNodeConfiguration extends NodeConfiguration{

	private String variable;	
	private String path;
	private String valName;
	
	
	public SetJsonNodeConfiguration() {
	
	}


	public SetJsonNodeConfiguration(String variable, String path, String valName) {
		super();
		this.variable = variable;
		this.path = path;
		this.valName = valName;
	}


	public String getVariable() {
		return variable;
	}


	public void setVariable(String variable) {
		this.variable = variable;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public String getValName() {
		return valName;
	}


	public void setValName(String valName) {
		this.valName = valName;
	}
	
	
	
	
}
