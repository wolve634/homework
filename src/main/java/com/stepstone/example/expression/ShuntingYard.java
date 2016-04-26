package com.stepstone.example.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

import static com.stepstone.example.expression.Constants.*;

import com.stepstone.example.exception.ExpressionException;

public class ShuntingYard {

	protected static Map<String, Operator> operators = new TreeMap<String, Operator>();

	protected static Map<String, Function> functions = new TreeMap<String, Function>();

	protected static Map<String, Double> variables = new TreeMap<String, Double>();

	/**
	 * Implementation of the RPN expression.
	 * 
	 * @param expression
	 *           
	 * @return An RPN representation of the expression
	 */
	public List<String> rpn(String expression) {
		List<String> outputQueue = new ArrayList<String>();
		Stack<String> stack = new Stack<String>();

		Tokenizer tokenizer = new Tokenizer(expression);

		String lastFunction = null;
		String previousToken = null;

		while (tokenizer.hasNext()) {
			String token = tokenizer.next();
			if (isNumber(token)) {
				outputQueue.add(token);
			} else if (variables.containsKey(token)) {
				outputQueue.add(token);
			} else if (functions.containsKey(token.toUpperCase())) {
				stack.push(token);
				lastFunction = token;
			} else if (Character.isLetter(token.charAt(0))) {
				stack.push(token);
			} else if (",".equals(token)) {
				while (!stack.isEmpty() && !"(".equals(stack.peek())) {
					outputQueue.add(stack.pop());
				}
				if (stack.isEmpty()) {
					throw new ExpressionException("Parse error for function '" + lastFunction + "'");
				}
			} else if (operators.containsKey(token)) {
				Operator o1 = operators.get(token);
				String token2 = stack.isEmpty() ? null : stack.peek();
				while (token2 != null && operators.containsKey(token2)
						&& ((o1.isLeftAssoc() && o1.getPrecedence() <= operators.get(token2).getPrecedence())
								|| (o1.getPrecedence() < operators.get(token2).getPrecedence()))) {
					outputQueue.add(stack.pop());
					token2 = stack.isEmpty() ? null : stack.peek();
				}
				stack.push(token);
			} else if ("(".equals(token)) {
				if (previousToken != null) {
					if (isNumber(previousToken)) {
						throw new ExpressionException("Missing operator at character position " + tokenizer.getPosition());
					}
					// if the ( is preceded by a valid function, then it
					// denotes the start of a parameter list
					if (functions.containsKey(previousToken.toUpperCase(Locale.ROOT))) {
						outputQueue.add(token);
					}
				}
				stack.push(token);
			} else if (")".equals(token)) {
				while (!stack.isEmpty() && !"(".equals(stack.peek())) {
					outputQueue.add(stack.pop());
				}
				if (stack.isEmpty()) {
					throw new ExpressionException("Mismatched parentheses");
				}
				stack.pop();
				if (!stack.isEmpty() && functions.containsKey(stack.peek().toUpperCase())) {
					outputQueue.add(stack.pop());
				}
			}
			previousToken = token;
		}
		while (!stack.isEmpty()) {
			String element = stack.pop();
			if ("(".equals(element) || ")".equals(element)) {
				throw new ExpressionException("Mismatched parentheses");
			}
			if (!operators.containsKey(element)) {
				throw new ExpressionException("Unknown operatoration: " + element);
			}
			outputQueue.add(element);
		}

		validate(outputQueue);

		return outputQueue;
	}

	/**
	 * 
	 *Checks whether the expression has a valid arithmetic representation
	 */
	private void validate(List<String> rpn) {
	
		int counter = 0;
		Stack<Integer> params = new Stack<Integer>();
		for (String token : rpn) {
			if ("(".equals(token)) {
				// is this a nested function call?
				if (!params.isEmpty()) {
					// increment the current function's param count
					// (the return of the nested function call
					// will be a parameter for the current function)
					params.set(params.size() - 1, params.peek() + 1);
				}
				// start a new parameter count
				params.push(0);
			} else if (!params.isEmpty()) {
				if (functions.containsKey(token.toUpperCase())) {
					// remove the parameters and the ( from the counter
					counter -= params.pop() + 1;
				} else {
					// increment the current function's param count
					params.set(params.size() - 1, params.peek() + 1);
				}
			} else if (operators.containsKey(token)) {
				// we have only binary operators
				counter -= 2;
			}
			if (counter < 0) {
				throw new ExpressionException("Too many operators or functions at: " + token);
			}
			counter++;
		}
		if (counter > 1) {
			throw new ExpressionException("Too many numbers or variables");
		} else if (counter < 1) {
			throw new ExpressionException("Invalid expression");
		}
	}

	private boolean isNumber(String str) {
		if (str.charAt(0) == MINUS_SIGNS && str.length() == 1)
			return false;
		if (str.charAt(0) == '+' && str.length() == 1)
			return false;
		if (str.charAt(0) == 'e' || str.charAt(0) == 'E')
			return false;
		for (char ch : str.toCharArray()) {
			if (!Character.isDigit(ch) && ch != MINUS_SIGNS && ch != DECIMAL_SEPARATOR && ch != 'e' && ch != 'E'
					&& ch != '+')
				return false;
		}
		return true;
	}

}
