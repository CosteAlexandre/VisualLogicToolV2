package com.visuallogictool.application.nodes.baseclassimpl;

import com.visuallogictool.application.jsonclass.NodeConfiguration;

public class ApiRestOutputConfiguration extends NodeConfiguration  {

	private String htm;
	private String var;
	
	public ApiRestOutputConfiguration() {
		// TODO Auto-generated constructor stub
	}

	public ApiRestOutputConfiguration(String htm, String var) {
		super();
		this.htm = htm;
		this.var = var;
	}

	public String getVar() {
		return var;
	}
	public void setVar(String var) {
		this.var = var;
	}
	public String getHtm() {
		return htm;
	}

	public void setHtm(String htm) {
		this.htm = htm;
	}



	
}
