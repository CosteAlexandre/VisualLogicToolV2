package com.visuallogictool.application.jsonclass;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import akka.protobuf.TextFormat.ParseException;

public class Node {

	private int id;
	private String function;
	private String className;
//	@JsonDeserialize(using = CustomDateDeserializer.class)
	private NodeConfiguration listParameters;
	private ArrayList<Integer> listNode;
	
	public Node() {
		// TODO Auto-generated constructor stub
	}
	
	public Node(int id, String function, String className, ArrayList<Integer> listNode) {
		super();
		this.id = id;
		this.function = function;

		this.listNode = listNode;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
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

	public ArrayList<Integer> getListNode() {
		return listNode;
	}
	public void setListNode(ArrayList<Integer> listNode) {
		this.listNode = listNode;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@JsonIgnore
	public Class getNodeClass() throws ClassNotFoundException {

      return Class.forName(this.className);

	}
	

	
	
}
