package com.visuallogictool.application.nodes.baseclassimpl;

import com.visuallogictool.application.jsonclass.NodeConfiguration;

public class CutEndNodeConfiguration extends NodeConfiguration {


	private String var;
	private int number;
	
	public CutEndNodeConfiguration() {
		// TODO Auto-generated constructor stub
	}


	public CutEndNodeConfiguration( String var, int number) {
		super();

		this.var = var;
		this.number = number;
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
