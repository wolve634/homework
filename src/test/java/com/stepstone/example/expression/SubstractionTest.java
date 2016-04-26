package com.stepstone.example.expression;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;


public class SubstractionTest extends BaseTest{
	
	
	
	@Test
	public void substractFromPositiveGreater(){
		assertThat(expression.eval("500 - 200")).isEqualTo(300);
		
	}
	
	@Test
	public void substractFromPositiveLesser(){
		assertThat(expression.eval("200 - 500")).isEqualTo(-300);
		
	}
	
	@Test
	public void substractZeroes(){
		assertThat(expression.eval("0 - 0")).isEqualTo(0);
		
	}
	
	@Test
	public void substractFromNegativeGreater(){
		assertThat(expression.eval("-200 - 500")).isEqualTo(-700);
		
	}
	
	@Test
	public void substractFromNegativeLesser(){
		assertThat(expression.eval("-500 - 200")).isEqualTo(-700);
		
	}
	
}
