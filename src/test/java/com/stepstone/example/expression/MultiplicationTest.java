package com.stepstone.example.expression;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class MultiplicationTest extends BaseTest {

	
	@Test
	public void positiveOnPositive(){
		assertThat(expression.eval("500 * 200")).isEqualTo(100000);
		
	}
	
	@Test
	public void substractZeroes(){
		assertThat(expression.eval("0 * 0")).isEqualTo(0);
		
	}
	
	@Test
	public void negativeOnPositive(){
		assertThat(expression.eval("-200 * 500")).isEqualTo(-100000);
		
	}
	
	@Test
	public void negativeOnNegative(){
		assertThat(expression.eval("-500 * -200")).isEqualTo(100000);
		
	}
	
	@Test
	public void nonZeroOnZero(){
		assertThat(expression.eval("-500 * 0")).isEqualTo(0);
		
	}
	
	@Test
	public void nonOneOnOne(){
		assertThat(expression.eval("-500 * 1")).isEqualTo(-500);
		
	}
}
