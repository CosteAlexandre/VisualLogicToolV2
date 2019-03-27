package com.visuallogictool.application.nodes.information.concrete;

public class TextboxField<T> extends FieldBase{
	private String type;
	
	
	public TextboxField() {
		// TODO Auto-generated constructor stub
	}
	
	public TextboxField(T value, String key, String label, boolean required, int order, String type) {
		super(value, key, label, required, order, "textbox");
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	
}
