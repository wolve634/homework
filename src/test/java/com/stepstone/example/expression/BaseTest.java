package com.stepstone.example.expression;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class BaseTest {

	protected Expression expression;

	@Before
	public void setUp() {
		this.expression = new Expression();
	}
	
	@Test
	public void fakeTest(){
		assertThat(true);
	}
}
