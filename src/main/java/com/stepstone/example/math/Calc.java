package com.stepstone.example.math;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import com.stepstone.example.expression.Expression; 

/**
 * A simple calculator program reading arithmetic expressions from the standard
 * input, evaluating them, and printing the results on the standard output.
 */
public class Calc {
	
	private Expression expression;
	
	public Calc(){
		expression = new Expression();
	}
	/**
	 * Evaluates an arithmetic expression. The grammar of accepted expressions
	 * is the following:
	 * 
	 * <code>
	 * 
	 *   expr ::= factor | expr ('+' | '-') expr
	 *   factor ::= term | factor ('*' | '/') factor
	 *   term ::= '-' term | '(' expr ')' | number | id | function | binding
	 *   number ::= int | decimal
	 *   int ::= '0' | posint
	 *   posint ::= ('1' - '9') | posint ('0' - '9')
	 *   decimal ::= int '.' ('0' - '9') | '.' ('0' - '9')
	 *   id ::= ('a' - 'z' | 'A' - 'Z' | '_') | id ('a' - 'z' | 'A' - 'Z' | '_' | '0' - '9')
	 *   function ::= ('sqrt' | 'log' | 'sin' | 'cos') '(' expr ')'
	 *   binding ::= id '=' expr
	 * 
	 * </code>
	 * 
	 * The binary operators are left-associative, with multiplication and division
	 * taking precedence over addition and subtraction.
	 * 
	 * Functions are implemented in terms of the respective static methods of
	 * the class java.lang.Math.
	 * 
	 * The bindings produced during the evaluation of the given expression
	 * are stored in a map, where they remain available for the evaluation
	 * of subsequent expressions.
	 * 
	 * Before leaving this method, the value of the given expression is bound
	 * to the special variable named "_".
	 * 
	 * @param expr well-formed arithmetic expression
	 * @return the value of the given expression
	 */
	public double eval(String expr) {
		// TODO: return the value of the given expression
		// some examples:
		// "1+2*3+4" returns 11.0
		// "(1+2)*(3+4)" returns 21.0
		// "sqrt(2)*sqrt(2) returns 2.0
		// "pi=3.14159265359" returns 3.14159265359
		//     and binds the identifier 'pi' to the same value
		// "cos(pi)" should then return -1
		
		// return new Expression().set
		//throw new UnsupportedOperationException();
		
		return expression.eval(expr);
	}
	
	public Map<String,Double> bindings() {
		return Expression.getBindings();
	}
	
	private final Map<String,Double> bindings = new TreeMap<String,Double>();
	
	public static void main(String[] args) throws IOException {
		Calc calc = new Calc();
		try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); 
				PrintWriter out = new PrintWriter(System.out, true)) { 
			while (true) {
				String line = in.readLine();
				if (line == null) {
					break;
				}
				line = line.trim();
				if (line.isEmpty()) {
					continue;
				}
				try {
					if (!line.startsWith(":")) {
						// handle expression
						out.println(calc.eval(line));
					} else {
						// handle command
						String[] command = line.split("\\s+", 2);
						switch (command[0]) {
							case ":vars":
								calc.bindings().forEach((name, value) ->
										out.println(name + " = " + value));
								break;
							case ":clear":
								if (command.length == 1) {
									// clear all
									calc.bindings().clear();
								} else {
									// clear requested
									calc.bindings().keySet().removeAll(Arrays.asList(command[1].split("\\s+")));
								}
								break;
							case ":exit":
							case ":quit":
								System.exit(0);
								break;
							default:
								throw new RuntimeException("unrecognized command: " + line);
						}
					}
				} catch (Exception ex) {
					System.err.println("*** ERROR: " + ex.getMessage());
				}
			}
		}
	}
}