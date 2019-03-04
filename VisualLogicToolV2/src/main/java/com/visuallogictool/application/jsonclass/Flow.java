package com.visuallogictool.application.jsonclass;
import java.util.ArrayList;

public class Flow {

	private int id;
	
	private ArrayList<Node> listNode;

	
	
	public Flow() {
		// TODO Auto-generated constructor stub
	}
	
	public Flow(int id, ArrayList<Node> listNode) {
		super();
		this.id = id;
		this.listNode = listNode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<Node> getListNode() {
		return listNode;
	}

	public void setListNode(ArrayList<Node> listNode) {
		this.listNode = listNode;
	}
	
	
	
	
	
	
}
