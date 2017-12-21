package com.ghostl.tddjava.gradle;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.mockito.Mockito.*;

import com.ghostl.tddjava.beans.TicTacToeBean;
import com.ghostl.tddjava.mongo.TicTacToeCollection;

public class TicTacToeSpec {
	
	private TicTacToeCollection collection;

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	private TicTacToe ticTacToe;
	
	@Before
	public final void before(){
		collection = mock(TicTacToeCollection.class);
		doReturn(true).when(collection).saveMove(any(TicTacToeBean.class));
		ticTacToe = new TicTacToe(collection);
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
	
	@Test
	public void givenTheFirstTurnWhenNextPlayerThenX(){
		assertEquals('X', ticTacToe.nextPlayer());
	}
	
	@Test
	public void givenLastTurnWasXWhenNextPlayerThenO(){
		ticTacToe.play(1, 1);
		assertEquals('O', ticTacToe.nextPlayer());
	}
	
	@Test
	public void whenPlayThenNoWinner(){
		String actual = ticTacToe.play(1,1);
		assertEquals("No Winner", actual);
	}
	
	@Test
	public void whenPlayAndWholeHorizontalLineThenWinner(){
		ticTacToe.play(1, 1);//X
		ticTacToe.play(1, 2);//O
		ticTacToe.play(2, 1);//X
		ticTacToe.play(2, 2);//O
		String actual = ticTacToe.play(3, 1);
		assertEquals("X is the Winner!!", actual);
	}
	
	@Test
	public void whenPlayAndWholeVerticalLineThenWinner(){
		ticTacToe.play(2, 1);//X
		ticTacToe.play(1, 1);//O
		ticTacToe.play(3, 1);//X
		ticTacToe.play(1, 2);//O
		ticTacToe.play(2, 2);//X
		String actual = ticTacToe.play(1, 3);//0
		assertEquals("O is the Winner!!", actual);
	}
	
	@Test
	public void whenPlayAndTopBottomDiagonalLineThenWinner(){
		ticTacToe.play(1, 1);//X
		ticTacToe.play(1, 2);//O
		ticTacToe.play(2, 2);//X
		ticTacToe.play(1, 3);//O
		String actual = ticTacToe.play(3, 3);//X
		assertEquals("X is the Winner!!", actual);
		
	}
	
	@Test
	public void whenPlayAndBottomTopDiagonalLineThenWinner(){
		ticTacToe.play(1, 3);//X
		ticTacToe.play(1, 1);//O
		ticTacToe.play(2, 2);//X
		ticTacToe.play(1, 2);//O
		String actual = ticTacToe.play(3, 1);//X
		assertEquals("X is the Winner!!", actual);
	}
	
	@Test
	public void whenAllBoxesAreFilledThenDraw(){
		ticTacToe.play(1, 1);//X
		ticTacToe.play(1, 2);//O
		ticTacToe.play(1, 3);//X
		ticTacToe.play(2, 1);//O
		ticTacToe.play(2, 3);//X
		ticTacToe.play(2, 2);//O
		ticTacToe.play(3, 1);//X
		ticTacToe.play(3, 3);//O
		String actual = ticTacToe.play(3, 2);
		assertEquals("The result is draw", actual);
	}
	
	@Test
	public void whenInstantiatedThenSetCollection(){
		assertNotNull(ticTacToe.getTicTacToeCollection());
	}
	
	@Test
	public void whenPlayThenSaveMoveIsInvoked(){
		TicTacToeBean move = new TicTacToeBean(1, 1, 3 , 'X');
		ticTacToe.play(move.getX(), move.getY());
		verify(collection).saveMove(move);
	}
	
	@Test
	public void whenPlayAndSaveReturnsFalseThenThrowException(){
		doReturn(false).when(collection).saveMove(any(TicTacToeBean.class));
		TicTacToeBean move = new TicTacToeBean(1, 1, 3, 'X');
		exception.expect(RuntimeException.class);
		ticTacToe.play(move.getX(), move.getY());
		
	}
	
	@Test
	public void whenPlayInvokedMultipleTimesThenTurnIncreases(){
		TicTacToeBean move1 = new TicTacToeBean(1, 1, 1, 'X');
		ticTacToe.play(move1.getX(), move1.getY());
		verify(collection, times(1)).saveMove(move1);
		TicTacToeBean move2 = new TicTacToeBean(2, 1, 2, 'O');
		ticTacToe.play(move2.getX(), move2.getY());
		verify(collection, times(1)).saveMove(move2);
	}
	
}
