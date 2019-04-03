package com.visuallogictool.application.configuration;

public class Configuration {

	private int port;
	private int mode;
	
	public Configuration() {
	}
	
	public Configuration(int port, int mode) {
		super();
		this.port = port;
		this.mode = mode;
	}


	public int getPort() {
		return port;
	}


	public void setPort(int port) {
		this.port = port;
	}


	public int getMode() {
		return mode;
	}


	public void setMode(int mode) {
		this.mode = mode;
	}
	
	
	
}
