package com.stepstone.example.expression;

public abstract class Function {

	private String name;

	private int attributeNumber;

	
	public Function(String name, int attributeNumber) {
		this.name = name.toUpperCase();
		this.attributeNumber = attributeNumber;
	}

	public String getName() {
		return name;
	}

	public int getAttributeNumber() {
		return attributeNumber;
	}

	public boolean numParamsVaries() { 
		return attributeNumber < 0;
	}

	
	public abstract Double eval(Double... parameters);

}
