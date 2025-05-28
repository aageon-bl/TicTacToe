// src/main/java/com/bnppf/tictactoe/domain/Board.java
package com.bnppf.tictactoe.domain;

import java.util.Arrays;

public class Board {
    private final Player[][] cells;
    private Player currentPlayer;
    private GameStatus gameStatus;
    private int movesMade;


    public Board() {
        cells = new Player[3][3]; // Initializes with nulls
        currentPlayer = Player.X;
        gameStatus = GameStatus.IN_PROGRESS; // Or NOT_STARTED
        movesMade = 0;
    }

    public Player getCell(int row, int col) {
        if (isValidCoordinate(row) && isValidCoordinate(col)) {
            return cells[row][col];
        }
        return null; // Or throw exception for invalid access, though tests don't cover this directly for getCell
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public Player[][] getCells() { // To return a copy for API response later
        Player[][] copy = new Player[3][3];
        for (int i = 0; i < 3; i++) {
            copy[i] = Arrays.copyOf(cells[i], 3);
        }
        return copy;
    }


    public boolean makeMove(int row, int col) {
        if (gameStatus != GameStatus.IN_PROGRESS) {
            return false; // Game is already over
        }
        if (!isValidCoordinate(row) || !isValidCoordinate(col) || cells[row][col] != null) {
            return false; // Invalid move
        }

        cells[row][col] = currentPlayer;
        movesMade++;
        // Check for win or draw AFTER the move (we'll add this logic next)
        // For now, just switch player
        switchPlayer();
        return true;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == Player.X) ? Player.O : Player.X;
    }

    private boolean isValidCoordinate(int c) {
        return c >= 0 && c < 3;
    }
}