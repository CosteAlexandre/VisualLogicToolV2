package com.visuallogictool.application.nodes.information.concrete;

public abstract class FieldBase<T> {

    protected T value;
    protected String key;
    protected String label;
    protected boolean required;
    protected int order;
    protected String controlType;
    
    public FieldBase() {
		// TODO Auto-generated constructor stub
	}
    
	protected FieldBase(T value, String key, String label, boolean required, int order, String controlType) {
		super();
		this.value = value;
		this.key = key;
		this.label = label;
		this.required = required;
		this.order = order;
		this.controlType = controlType;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getControlType() {
		return controlType;
	}

	public void setControlType(String controlType) {
		this.controlType = controlType;
	}
    
    
    
    
    
}
