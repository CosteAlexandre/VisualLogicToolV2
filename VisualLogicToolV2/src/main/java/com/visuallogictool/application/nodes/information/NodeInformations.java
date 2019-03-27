package com.visuallogictool.application.nodes.information;

import java.util.ArrayList;

import com.visuallogictool.application.nodes.information.concrete.FieldBase;

public class NodeInformations {

	private String name;	
	private String tooltip;	
	private String description;
	private String type;
	private ArrayList<Field> fields;
	
	private ArrayList<FieldBase> fieldBases;
	
	private String className;
	private String classType;
	
	private String shortName;
	
	public NodeInformations() {
		fields = new ArrayList<Field>();
		fieldBases = new ArrayList<FieldBase>();
	}

	
	

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public ArrayList<FieldBase> getFieldBases() {
		return fieldBases;
	}

	public void setFieldBases(ArrayList<FieldBase> fieldBases) {
		this.fieldBases = fieldBases;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<Field> getFields() {
		return fields;
	}

	public void setFields(ArrayList<Field> fields) {
		this.fields = fields;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
	
}
