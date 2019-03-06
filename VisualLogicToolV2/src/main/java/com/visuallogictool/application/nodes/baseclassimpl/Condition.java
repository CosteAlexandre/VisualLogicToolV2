package com.visuallogictool.application.nodes.baseclassimpl;

public class Condition {

	private String val1;
	private String val2;
	
	private String typeVal2;
	
	private String condition;
	private int outPut;
	
	
	public Condition() {
		// TODO Auto-generated constructor stub
	}


	public Condition(String val1, String val2, String typeVal2, String condition, int outPut) {
		super();
		this.val1 = val1;
		this.val2 = val2;
		this.typeVal2 = typeVal2;
		this.condition = condition;
		this.outPut = outPut;
	}


	public String getVal1() {
		return val1;
	}


	public void setVal1(String val1) {
		this.val1 = val1;
	}


	public String getVal2() {
		return val2;
	}


	public void setVal2(String val2) {
		this.val2 = val2;
	}


	public String getTypeVal2() {
		return typeVal2;
	}


	public void setTypeVal2(String typeVal2) {
		this.typeVal2 = typeVal2;
	}


	public String getCondition() {
		return condition;
	}


	public void setCondition(String condition) {
		this.condition = condition;
	}


	public int getOutPut() {
		return outPut;
	}


	public void setOutPut(int outPut) {
		this.outPut = outPut;
	}


	
	
}
