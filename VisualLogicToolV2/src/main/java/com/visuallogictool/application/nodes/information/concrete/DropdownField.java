package com.visuallogictool.application.nodes.information.concrete;

import java.util.ArrayList;

public class DropdownField<T> extends FieldBase{
	protected ArrayList<Option> options;
	
	
	public DropdownField() {
		// TODO Auto-generated constructor stub
	}
	
	public DropdownField(T value, String key, String label, boolean required, int order, ArrayList<Option> options) {
		super(value, key, label, required, order, "dropdown");
		this.options = options;
	}

	public ArrayList<Option> getOptions() {
		return options;
	}

	public void setOptions(ArrayList<Option> options) {
		this.options = options;
	}
	
	
	
	
	
}
