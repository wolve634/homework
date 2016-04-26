package com.stepstone.example.expression;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ComplexExpressionTest extends BaseTest {
	
	@Test
	public void test1(){
		assertThat(expression.eval("(500 + 1) * 2")).isEqualTo(1002);
		
	}
	
	@Test
	public void test2(){
		assertThat(expression.eval("((500 * 2 + 2)-2)^2*2 ")).isEqualTo(2000000);
		
	}
	
	@Test
	public void testPI(){
		assertThat(expression.eval("2 * PI")).isBetween(6.28,6.29);
		
	}
	
	@Test
	public void testCos(){
		assertThat(expression.eval("cos(PI)")).isEqualTo(-1);
		
	}
	
	@Test
	public void testAddVariable(){
		int size = expression.getBindings().size();
		assertThat(expression.eval("t = (35 + -35)")).isEqualTo(0);
		assertThat(expression.getBindings().size()).isEqualTo(size + 1);
	}
	
	@Test
	public void testNewlyAddedVariable(){
		expression.eval("myVar = 1+1");
		assertThat(expression.eval("100 * myVar")).isEqualTo(200);
	}



}
