package com.visuallogictool.application.jsonclass;
import java.io.IOException;
import java.util.ArrayList;

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
	private String listParameters;
	private ArrayList<String> listNode;
	
	public Node() {
		// TODO Auto-generated constructor stub
	}
	
	public Node(int id, String function, String className, String listParameters, ArrayList<String> listNode) {
		super();
		this.id = id;
		this.function = function;
		this.listParameters = listParameters;
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
	public String getListParameters() {
		return listParameters;
	}
	public void setListParameters(String listParameters) {
		this.listParameters = listParameters;
	}
	public ArrayList<String> getListNode() {
		return listNode;
	}
	public void setListNode(ArrayList<String> listNode) {
		this.listNode = listNode;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	

public class CustomDateDeserializer extends StdDeserializer<String> {
 
   
 
    public CustomDateDeserializer(){
        this(null);
    }
    public CustomDateDeserializer(Class<?> c){
        super(c);
    }
 
    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext
            deserializationContext) throws IOException, JsonProcessingException {
        String date = jsonParser.getText();
        System.out.println("In custom parser : "+date);
        return date;
    }
}
	
	
}
