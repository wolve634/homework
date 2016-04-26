package com.stepstone.example.exception;


public class ExpressionException extends RuntimeException {
	private static final long serialVersionUID = 1461550711130L;

	public ExpressionException(String message) {
		super(message);
	}
}