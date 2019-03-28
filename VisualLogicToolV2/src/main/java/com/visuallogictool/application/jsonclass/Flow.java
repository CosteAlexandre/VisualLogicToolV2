package com.visuallogictool.application.jsonclass;
import java.util.ArrayList;

public class Flow {

	private String id;
	
	private ArrayList<Node> listNode;

	private Object graph;
	
	public Flow() {
		// TODO Auto-generated constructor stub
	}
	
	public Flow(String id, ArrayList<Node> listNode, Object graph) {
		super();
		this.id = id;
		this.listNode = listNode;
		this.graph = graph;
	}
	public Object getGraph() {
		return graph;
	}
	
	public void setGraph(Object graph) {
		this.graph = graph;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<Node> getListNode() {
		return listNode;
	}

	public void setListNode(ArrayList<Node> listNode) {
		this.listNode = listNode;
	}
	
	
	
	
	
	
}
