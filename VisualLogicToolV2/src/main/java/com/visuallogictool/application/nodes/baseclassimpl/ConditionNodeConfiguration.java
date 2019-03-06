package com.visuallogictool.application.nodes.baseclassimpl;

import java.util.ArrayList;

import com.visuallogictool.application.jsonclass.NodeConfiguration;

public class ConditionNodeConfiguration extends NodeConfiguration{

	private ArrayList<Condition> conditionList;
	

	
	
	
	public ConditionNodeConfiguration() {
		// TODO Auto-generated constructor stub
	}





	public ConditionNodeConfiguration(ArrayList<Condition> conditionList) {
		super();
		this.conditionList = conditionList;
	}

	public ArrayList<Condition> getConditionList() {
		return conditionList;
	}

	public void setConditionList(ArrayList<Condition> conditionList) {
		this.conditionList = conditionList;
	}
	
	
	
	
}
