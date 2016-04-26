package com.stepstone.example.expression;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class AdditionTest  extends BaseTest {
	
	@Test
	public void addsOne(){
		assertThat(expression.eval("500 + 1")).isEqualTo(501);
		
	}
	
	@Test
	public void addsZero(){
		assertThat(expression.eval("500 + 0")).isEqualTo(500);
		
	}
	
	@Test
	public void addsNothing(){
		assertThat(expression.eval("-553")).isEqualTo(-553);
		
	}
	
	@Test
	public void negativePlusPozitive(){
		assertThat(expression.eval("-5 + 35")).isEqualTo(30);
		
	}
	
	@Test
	public void pozitivePlusNegative(){
		assertThat(expression.eval("35 + -35")).isEqualTo(0);
	}

}
