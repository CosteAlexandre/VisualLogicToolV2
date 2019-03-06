package com.visuallogictool.application.nodes.baseclassimpl;

import com.visuallogictool.application.jsonclass.NodeConfiguration;

public class HttpRequestConfiguration extends NodeConfiguration{

	private String var;
	private String url;
	
	public HttpRequestConfiguration() {
		// TODO Auto-generated constructor stub
	}

	public HttpRequestConfiguration(String var, String url) {
		super();
		this.var = var;
		this.url = url;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
}
