package com.ghostl.tddjava.gradle;

import java.net.UnknownHostException;

import com.ghostl.tddjava.beans.TicTacToeBean;
import com.ghostl.tddjava.mongo.TicTacToeCollection;

public class TicTacToe {

	private Character[][] board = { { '\0', '\0', '\0' }, { '\0', '\0', '\0' }, { '\0', '\0', '\0' } };
	private char lastPlayer = '\0';
	
	private int turn = 0;

	private String RESULT_DRAW = "The result is draw";
	private String NO_WINNER = "No Winner";
	
	private static final int SIZE = 3;
	private TicTacToeCollection ticTacToeCollection;
	
	public TicTacToe() throws UnknownHostException {
	    this(new TicTacToeCollection());
	}
	protected TicTacToe (TicTacToeCollection collection) {
	    ticTacToeCollection = collection;
	}
	
	public TicTacToeCollection getTicTacToeCollection() {
		return ticTacToeCollection;
	}


	public void setTicTacToeCollection(TicTacToeCollection ticTacToeCollection) {
		this.ticTacToeCollection = ticTacToeCollection;
	}
	
	
	public String play(int x, int y) {

		checkAxis(x);
		checkAxis(y);
		lastPlayer = nextPlayer();
		//setBox(x, y, lastPlayer);
		setBox(new TicTacToeBean(++turn, x, y, lastPlayer));
		if (isWin(x, y)) {
			return lastPlayer + " is the Winner!!";
		} else if (isDraw()) {
			return RESULT_DRAW;//"The result is draw";
		}
		return NO_WINNER;//"No Winner";

	}

	private boolean isDraw() {
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < SIZE; y++) {
				if (board[x][y] == '\0') {
					return false;
				}
			}
		}
		return true;
	}

	private boolean isWin(int x, int y) {
		int playerTotal = lastPlayer * 3;
		char diagonal1, diagonal2, vertical, horizontal;
		diagonal1 = diagonal2 = vertical = horizontal = '\0';
		for (int i = 0; i < SIZE; i++) {
			horizontal += board[i][y - 1];
			vertical += board[x - 1][i];
			diagonal1 += board[i][i];
			diagonal2 += board[i][SIZE - i - 1];
			if (horizontal == playerTotal 
					|| vertical == playerTotal 
					|| diagonal1 == playerTotal
					|| diagonal2 == playerTotal) {
				return true;
			}
		}
		return false;
	}

	private void setBox(TicTacToeBean bean) {
		if (board[bean.getX() - 1][bean.getY() - 1] != '\0') {
			throw new RuntimeException("Box is occupied");
		} else {
			board[bean.getX() - 1][bean.getY() - 1] = lastPlayer;
			getTicTacToeCollection().saveMove(bean);
			if(!getTicTacToeCollection().saveMove(bean)){
				throw new RuntimeException("Saving to DB failed");
			}
		}

	}

	private void checkAxis(int axis) {
		if (axis > 3 || axis < 0)
			throw new RuntimeException("X is exceed their boundaries");
	}

	public char nextPlayer() {
		if (lastPlayer == 'X') {
			return 'O';
		}
		return 'X';
	}

	

}
