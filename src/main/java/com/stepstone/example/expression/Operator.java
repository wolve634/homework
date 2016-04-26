package com.stepstone.example.expression;


public abstract class Operator {

	private String operator;
	
	private int precedence;
	
	private boolean leftAssoc;

	
	public Operator(String operator, int precedence, boolean leftAssoc) {
		this.operator = operator;
		this.precedence = precedence;
		this.leftAssoc = leftAssoc;
	}

	public String getOperator() {
		return this.operator;
	}

	public int getPrecedence() {
		return precedence;
	}

	public boolean isLeftAssoc() {
		return leftAssoc;
	}
	
	public abstract Double eval(Double... parameters);

}
