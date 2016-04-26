package com.stepstone.example.expression;

import java.util.Iterator;

import com.stepstone.example.exception.ExpressionException;

import static com.stepstone.example.expression.Constants.*;

public class Tokenizer implements Iterator<String> {

	private int position = 0;
	private String input;
	private String previousToken;

	public Tokenizer(String input) {
		this.input = input.trim();
	}

	@Override
	public boolean hasNext() {
		return (position < input.length());
	}

	private char peekNextChar() {
		if (position < (input.length() - 1)) {
			return input.charAt(position + 1);
		} else {
			return 0;
		}
	}

	@Override
	public String next() {
		StringBuilder token = new StringBuilder();
		if (!hasNext()) {
			return previousToken = null;
		}
		char ch = input.charAt(position);
		while (Character.isWhitespace(ch) && hasNext()) {
			ch = input.charAt(++position);
		}
		if (Character.isDigit(ch)) {
			while ((Character.isDigit(ch) || ch == DECIMAL_SEPARATOR || isE(ch,token) && (hasNext()))) {
				token.append(input.charAt(position++));
				ch = position == input.length() ? 0 : input.charAt(position);
			}
		} else if (ch == MINUS_SIGNS && Character.isDigit(peekNextChar())
				   && ("(".equals(previousToken) || ",".equals(previousToken)
				   || previousToken == null || ShuntingYard.operators.containsKey(previousToken))) {
			
			token.append(MINUS_SIGNS);
			position++;
			token.append(next());
		} else if (Character.isLetter(ch) || (ch == '_')) {
			while ((Character.isLetter(ch) || Character.isDigit(ch) || (ch == '_'))
					&& (position < input.length())) {
				
				token.append(input.charAt(position++));
				ch = position == input.length() ? 0 : input.charAt(position);
			}
		} else if (ch == '(' || ch == ')' || ch == ',') {
			token.append(ch);
			position++;
		} else {
			while (!Character.isLetter(ch) && !Character.isDigit(ch)
					&& ch != '_' && !Character.isWhitespace(ch)
					&& ch != '(' && ch != ')' && ch != ','
					&& (position < input.length())) {
				
				token.append(input.charAt(position));
				position++;
				ch = position == input.length() ? 0 : input.charAt(position);
				
				if (ch == MINUS_SIGNS) {
					break;
				}
			}
			if (!ShuntingYard.operators.containsKey(token.toString())) {
				throw new ExpressionException("Invalid operator '" + token
						+ "' at positionition " + (position - token.length() + 1));
			}
		}
		return previousToken = token.toString();
	}

	public int getPosition() {
		return this.position;
	}

	private boolean isE(Character input, StringBuilder token) {
		return (input == 'e' || input == 'E' || (input == MINUS_SIGNS && token.length() > 0
			&& ('e' == token.charAt(token.length() - 1) || 'E' == token.charAt(token.length() - 1)))
			|| (input == '+' && token.length() > 0 && ('e' == token.charAt(token.length() - 1)
			|| 'E' == token.charAt(token.length() - 1))));
	}
}
