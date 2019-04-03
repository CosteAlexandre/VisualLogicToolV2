package com.visuallogictool.application.jsonclass;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Node {

	private String id;
	private String function;
	private String className;
//	@JsonDeserialize(using = CustomDateDeserializer.class)
	private NodeConfiguration listParameters;
	private ArrayList<ArrayList<String>> output;
	
	public Node() {
		// TODO Auto-generated constructor stub
	}
	
	public Node(String id, String function, String className, ArrayList<ArrayList<String>> output) {
		super();
		this.id = id;
		this.function = function;

		this.output = output;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}


	public NodeConfiguration getListParameters() {
		return listParameters;
	}

	public void setListParameters(NodeConfiguration listParameters) {
		this.listParameters = listParameters;
	}

	public ArrayList<ArrayList<String>> getOutput() {
		return output;
	}

	public void setOutput(ArrayList<ArrayList<String>> output) {
		this.output = output;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@JsonIgnore
	public Class<?> getNodeClass() throws ClassNotFoundException {

      return Class.forName(this.className);

	}
	

	
	
}
