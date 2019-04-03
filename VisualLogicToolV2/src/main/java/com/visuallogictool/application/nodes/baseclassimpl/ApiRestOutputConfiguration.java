package com.visuallogictool.application.nodes.baseclassimpl;

import com.visuallogictool.application.jsonclass.NodeConfiguration;

public class ApiRestOutputConfiguration extends NodeConfiguration  {

	private String htm;
	
	public ApiRestOutputConfiguration() {
		// TODO Auto-generated constructor stub
	}

	public ApiRestOutputConfiguration(String htm) {
		super();
		this.htm = htm;
	}

	public String getHtm() {
		return htm;
	}

	public void setHtm(String htm) {
		this.htm = htm;
	}



	
}
