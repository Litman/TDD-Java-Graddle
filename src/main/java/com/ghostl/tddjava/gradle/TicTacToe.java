package com.ghostl.tddjava.gradle;

public class TicTacToe {

	private Character[][] board = { { '\0', '\0', '\0' }, { '\0', '\0', '\0' }, { '\0', '\0', '\0' } };
	private char lastPlayer = '\0';

	public void play(int x, int y) {

		checkAxis(x);
		checkAxis(y);
		setBox(x, y);
		lastPlayer = nextPlayer();

	}

	private void checkAxis(int axis) {
		if (axis > 3 || axis < 0)
			throw new RuntimeException("X is exceed their boundaries");
	}

	private void setBox(int x, int y) {
		if (board[x - 1][y - 1] != '\0') {
			throw new RuntimeException("Box is occupied");
		} else {
			board[x - 1][y - 1] = 'X';
		}
	}

	public char nextPlayer() {
		if (lastPlayer == 'X') {
			return 'O';
		}
		return 'X';
	}

}
