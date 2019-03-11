package com.visuallogictool.application.nodes.baseclassimpl;

import java.util.ArrayList;

import com.visuallogictool.application.jsonclass.NodeConfiguration;

public class ConditionNodeConfiguration extends NodeConfiguration{

	private ArrayList<Condition> conditions;
	

	
	
	
	public ConditionNodeConfiguration() {
		// TODO Auto-generated constructor stub
	}





	public ConditionNodeConfiguration(ArrayList<Condition> conditions) {
		super();
		this.conditions = conditions;
	}

	public ArrayList<Condition> getConditions() {
		return conditions;
	}

	public void setConditions(ArrayList<Condition> conditions) {
		this.conditions = conditions;
	}
	
	
	
	
}
