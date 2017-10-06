package com.ghostl.tddjava.gradle;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TicTacToeSpec {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	private TicTacToe ticTacToe;
	
	@Before
	public final void before(){
		ticTacToe = new TicTacToe();
	}
	
	@Test
	public void whenXExceedBoardThenRuntimeExcepetion(){
		exception.expect(RuntimeException.class);
		ticTacToe.play(5,2);
	}
	
	@Test
	public void whenYExceedBoardThenRuntimeException(){
		exception.expect(RuntimeException.class);
		ticTacToe.play(2, 5);
	}
	
	@Test
	public void whenOccupiedThenRuntimeException(){
		ticTacToe.play(2, 1);
		exception.expect(RuntimeException.class);
		ticTacToe.play(2, 1);
		
	}
}