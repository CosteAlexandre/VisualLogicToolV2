package com.visuallogictool.application.nodes.information;

public class Field {

	private String name;
	private String type;
	private String description;
	private String tooltip;
	
	
	public Field(String name, String type, String description, String tooltip) {
		super();
		this.name = name;
		this.type = type;
		this.description = description;
		this.tooltip = tooltip;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTooltip() {
		return tooltip;
	}
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	
	
	
	
	
}
