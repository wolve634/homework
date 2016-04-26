package com.stepstone.example.expression;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.stepstone.example.exception.ExpressionException;

public class DivisionTest extends BaseTest {

	
	@Test
	public void positiveOnOne(){
		assertThat(expression.eval("500 / 1")).isEqualTo(500);
		
	}
	
	@Test
	public void positiveOnTwo(){
		assertThat(expression.eval("500 / 2")).isEqualTo(250);
		
	}
	
	@Test
	public void negativeOnTwo(){
		assertThat(expression.eval("-553 / 2")).isEqualTo(-276.5);
		
	}
	
	@Test
	public void zeroOnTwo(){
		assertThat(expression.eval("0 / 2")).isEqualTo(0);
		
	}
	
	@Test(expected = ExpressionException.class)
	public void nonZeroOnZero(){
		expression.eval("5 / 0");
	}
}
