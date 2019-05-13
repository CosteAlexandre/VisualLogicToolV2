package com.visuallogictool.application.nodes.information;

import com.visuallogictool.application.nodes.information.concrete.FieldBase;

public class NodeInformationsSetUp {

	private NodeInformations nodeInformations;
	
	public NodeInformationsSetUp() {
		nodeInformations = new NodeInformations();
	}
	
	public NodeInformationsSetUp setHeader(String name, String tooltip,String description) {
		
		nodeInformations.setName(name);
		nodeInformations.setTooltip(tooltip);
		nodeInformations.setDescription(description);
		
		return this;
	}
	
	public NodeInformationsSetUp addFieldDescription(Field ...fields ) {
		
		for (Field field : fields) {
			nodeInformations.getFields().add(field);
		}
		
		return this;
	}
	public NodeInformations getNodeInformations() {
		return nodeInformations;
	}
	public NodeInformationsSetUp addFieldDefinition(FieldBase ...basefields) {
		
		for (FieldBase fieldBase : basefields) {
			this.nodeInformations.getFieldBases().add(fieldBase);
		}
		
		return this;
	}
	public NodeInformationsSetUp setType(String type, String labelType) {
		this.nodeInformations.setType(type);
		this.nodeInformations.setLabelType(labelType);
		return this;
	}
	public NodeInformationsSetUp setClass(String classType, String className) {
		
		this.nodeInformations.setClassType(classType);
		this.nodeInformations.setClassName(className);		
		
		return this;
	}
	public NodeInformationsSetUp setShortName(String shortName) {
		this.nodeInformations.setShortName(shortName);
		return this;
	}
	
	public NodeInformationsSetUp setColor(String color) {
		this.nodeInformations.setColor(color);
		return this;
	}
	
	public NodeInformationsSetUp setImageUrl(String imageUrl){
		this.nodeInformations.setImageUrl(imageUrl);
		return this;
	}
	
}
