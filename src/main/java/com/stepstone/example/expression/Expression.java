package com.stepstone.example.expression;

import static com.stepstone.example.expression.Constants.E;
import static com.stepstone.example.expression.Constants.ONE;
import static com.stepstone.example.expression.Constants.PI;
import static com.stepstone.example.expression.Constants.ZERO;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

import com.stepstone.example.exception.ExpressionException;


public class Expression extends ShuntingYard {

	private static final Double LEFT_PARANTHESIS = new Double(0);

	private void setVariables(){
		variables.put("PI", PI);
		variables.put("E", E);
		variables.put("TRUE", ONE);
		variables.put("FALSE", ZERO);
	}
	
	public Expression() {
		
		setVariables();

		addOperator(new Operator("-", 2, true) {
			@Override
			public Double eval(Double... parameters) {
				return parameters[1] - parameters[0];
			}
		});
		
		addOperator(new Operator("+", 2, true) {
			@Override
			public Double eval(Double... parameters) {
				return new BigDecimal(parameters[0]).add(new BigDecimal(parameters[1])).doubleValue();
			}
		});
		
		addOperator(new Operator("/", 3, true) {
			@Override
			public Double eval(Double... parameters) {
				if(parameters[0] == 0){
					throw new ExpressionException("Division on zero is forbidden");
				}
				return parameters[1] / parameters[0];
			}
		});
		
		addOperator(new Operator("*", 3, true) {
			@Override
			public Double eval(Double... parameters) {
				return new BigDecimal(parameters[0]).multiply(new BigDecimal(parameters[1])).doubleValue();
			}
		});
		
		addOperator(new Operator("^", 4, false) {
			@Override
			public Double eval(Double... parameters) {
				BigDecimal base = new BigDecimal(parameters[1]);

				return base.pow(parameters[0].intValue()).doubleValue();
			}
		});
		
		addOperator(new Operator("%", 3, true) {
			@Override
			public Double eval(Double... parameters) {
				return parameters[0] % parameters[1];
			}
		});
		
		addOperator(new Operator("&&", 4, false) {
			@Override
			public Double eval(Double... parameters) {
				boolean b1 = !parameters[0].equals(ZERO);
				boolean b2 = !parameters[1].equals(ZERO);
				return b1 && b2 ? ONE : ZERO;
			}
		});

		addOperator(new Operator("||", 2, false) {
			@Override
			public Double eval(Double... parameters) {
				boolean b1 = !parameters[0].equals(ZERO);
				boolean b2 = !parameters[1].equals(ZERO);
				return b1 || b2 ? ONE : ZERO;
			}
		});

		addOperator(new Operator(">", 1, false) {
			@Override
			public Double eval(Double... parameters) {
				return parameters[0].compareTo(parameters[1]) == 1 ? ONE : ZERO;
			}
		});

		addOperator(new Operator(">=", 1, false) {
			@Override
			public Double eval(Double... parameters) {
				return parameters[0].compareTo(parameters[1]) >= 0 ? ONE : ZERO;
			}
		});

		addOperator(new Operator("<", 1, false) {
			@Override
			public Double eval(Double... parameters) {
				return parameters[0].compareTo(parameters[1]) == -1 ? ONE : ZERO;
			}
		});

		addOperator(new Operator("<=", 1, false) {
			@Override
			public Double eval(Double... parameters) {
				return parameters[0].compareTo(parameters[1]) <= 0 ? ONE : ZERO;
			}
		});

		addOperator(new Operator("=", 7, false) {
			@Override
			public Double eval(Double... parameters) {
				return parameters[0].compareTo(parameters[1]) == 0 ? ONE : ZERO;
			}
		});
		addOperator(new Operator("==", 7, false) {
			@Override
			public Double eval(Double... parameters) {
				return operators.get("=").eval(parameters[0], parameters[1]);
			}
		});

		addOperator(new Operator("!=", 7, false) {
			@Override
			public Double eval(Double... parameters) {
				return parameters[0].compareTo(parameters[1]) != 0 ? ONE : ZERO;
			}
		});
		addOperator(new Operator("<>", 7, false) {
			@Override
			public Double eval(Double... parameters) {
				return operators.get("!=").eval(parameters[0], parameters[1]);
			}
		});

		addFunction(new Function("NOT", 1) {
			@Override
			public Double eval(Double... parameters) {
				boolean zero = parameters[0].equals(0.0);
				return zero ? ONE : ZERO;
			}
		});


		addFunction(new Function("RANDOM", 0) {
			@Override
			public Double eval(Double... parameters) {
				return StrictMath.random();
			}
		});
		addFunction(new Function("SIN", 1) {
			@Override
			public Double eval(Double... parameters) {
				return StrictMath.sin(Math.toRadians(parameters[0]));
			}
		});
		addFunction(new Function("COS", 1) {
			@Override
			public Double eval(Double... parameters) {
				return StrictMath.cos(parameters[0]);
			}
		});
		addFunction(new Function("TAN", 1) {
			@Override
			public Double eval(Double... parameters) {
				return StrictMath.tan(Math.toRadians(parameters[0]));

			}
		});
		addFunction(new Function("ASIN", 1) {
			@Override
			public Double eval(Double... parameters) {
				return StrictMath.toDegrees(StrictMath.asin(parameters[0]));
			}
		});
		addFunction(new Function("ACOS", 1) {
			@Override
			public Double eval(Double... parameters) {
				return StrictMath.toDegrees(StrictMath.acos(parameters[0]));

			}
		});
		addFunction(new Function("ATAN", 1) {
			@Override
			public Double eval(Double... parameters) {
				return StrictMath.toDegrees(StrictMath.atan(parameters[0]));

			}
		});

		addFunction(new Function("MAX", -1) {
			@Override
			public Double eval(Double... parameters) {
				Double max = null;

				for (Double parameter : parameters) {
					if (max == null || parameter.compareTo(max) > 0) {
						max = parameter;
					}
				}
				return max;
			}
		});
		addFunction(new Function("MIN", -1) {
			@Override
			public Double eval(Double... parameters) {
				Double min = null;

				for (Double parameter : parameters) {
					if (min == null || parameter.compareTo(min) < 0) {
						min = parameter;
					}
				}
				return min;
			}
		});
		addFunction(new Function("ABS", 1) {
			@Override
			public Double eval(Double... parameters) {
				return StrictMath.abs(parameters[0]);
			}
		});
		addFunction(new Function("LOG", 1) {
			@Override
			public Double eval(Double... parameters) {
				return StrictMath.log(parameters[0]);
			}
		});

		addFunction(new Function("FLOOR", 1) {
			@Override
			public Double eval(Double... parameters) {
				Double toRound = parameters[0];
				return StrictMath.floor(toRound);
			}
		});
		addFunction(new Function("CEILING", 1) {
			@Override
			public Double eval(Double... parameters) {
				Double toRound = parameters[0];
				return StrictMath.ceil(toRound);
			}
		});
		addFunction(new Function("SQRT", 1) {
			@Override
			public Double eval(Double... parameters) {
				return StrictMath.sqrt(parameters[0]);
			}
		});

	}

	public Double eval(String expression) {
		Stack<Double> stack = new Stack<Double>();
		int possibleVariableIndex = expression.indexOf("=");

		String variable = null;
		if (possibleVariableIndex > -1) {

			variable = expression.substring(0, possibleVariableIndex).trim();
			expression = expression.substring(possibleVariableIndex + 1);
		}

		for (String token : rpn(expression)) {
			if (operators.containsKey(token)) {
				stack.push(operators.get(token).eval(stack.pop(), stack.pop()));
			} else if (variables.containsKey(token)) {
				stack.push(variables.get(token));
			} else if (functions.containsKey(token.toUpperCase())) {
				Function function = functions.get(token.toUpperCase());
				Double[] parameters = new Double[!function.numParamsVaries() ? function.getAttributeNumber() : 0];
				// pop parameters off the stack until we hit the start of
				// this function's parameter list
				int counter = 0;
				while (!stack.isEmpty() && stack.peek() != LEFT_PARANTHESIS) {
					parameters[counter] = stack.pop();
					counter++;
				}
				if (stack.peek() == LEFT_PARANTHESIS) {
					stack.pop();
				}
				if (!function.numParamsVaries() && parameters.length != function.getAttributeNumber()) {
					throw new ExpressionException("Function " + token + " expected " + function.getAttributeNumber()
							+ " parameters, got " + parameters.length);
				}
				
				stack.push(function.eval(parameters));
			} else if ("(".equals(token)) {
				stack.push(LEFT_PARANTHESIS);
			} else {
				stack.push(new Double(token));
			}
		}

		if (Objects.nonNull(variable)){
			variables.put(variable, stack.lastElement());
		}

		return stack.pop();
	}

	
	public Operator addOperator(Operator operator) {
		return operators.put(operator.getOperator(), operator);
	}

	public Function addFunction(Function function) {
		return functions.put(function.getName(), function);
	}

	public static Map<String, Double> getBindings(){
		return variables;
	}
}