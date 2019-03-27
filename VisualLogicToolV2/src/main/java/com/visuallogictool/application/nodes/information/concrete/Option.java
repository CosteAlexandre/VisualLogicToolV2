package com.visuallogictool.application.nodes.information.concrete;

public class Option {

    private String value;
    private String key;
	
    public Option() {
		// TODO Auto-generated constructor stub
	}
    

	public Option(String value, String key) {
		super();
		this.value = value;
		this.key = key;
	}


	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
    
    
	
}
