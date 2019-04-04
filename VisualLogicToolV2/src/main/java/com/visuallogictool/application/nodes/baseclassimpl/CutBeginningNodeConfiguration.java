package com.visuallogictool.application.nodes.baseclassimpl;

import com.visuallogictool.application.jsonclass.NodeConfiguration;

public class CutBeginningNodeConfiguration extends NodeConfiguration {


	private String var;
	private int number;
	private String newVariable;
	
	public CutBeginningNodeConfiguration() {
		// TODO Auto-generated constructor stub
	}


	public CutBeginningNodeConfiguration( String var, int number, String newVariable) {
		super();

		this.var = var;
		this.number = number;
		this.newVariable = newVariable;
	}

	public String getNewVariable() {
		return newVariable;
	}
	public void setNewVariable(String newVariable) {
		this.newVariable = newVariable;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getVar() {
		return var;
	}
	public void setVar(String var) {
		this.var = var;
	}


	
	
}
