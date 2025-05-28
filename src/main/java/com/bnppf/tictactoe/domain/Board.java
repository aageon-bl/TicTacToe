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
        if (gameStatus != GameStatus.IN_PROGRESS) { // Check if game is already over
            return false;
        }
        if (!isValidCoordinate(row) || !isValidCoordinate(col) || cells[row][col] != null) {
            return false;
        }

        cells[row][col] = currentPlayer;
        movesMade++;

        if (checkWin(row, col)) {
            gameStatus = (currentPlayer == Player.X) ? GameStatus.X_WINS : GameStatus.O_WINS;
        } else if (movesMade == 9) { // All cells filled
            gameStatus = GameStatus.DRAW;
        } else {
            switchPlayer();
        }
        return true;
    }

    private boolean checkWin(int r, int c) {
        // Check row
        if (cells[r][0] == currentPlayer && cells[r][1] == currentPlayer && cells[r][2] == currentPlayer) {
            return true;
        }
        // Check column
        if (cells[0][c] == currentPlayer && cells[1][c] == currentPlayer && cells[2][c] == currentPlayer) {
            return true;
        }
        // Check diagonals
        if (r == c) { // On main diagonal
            if (cells[0][0] == currentPlayer && cells[1][1] == currentPlayer && cells[2][2] == currentPlayer) {
                return true;
            }
        }
        if (r + c == 2) { // On anti-diagonal
            if (cells[0][2] == currentPlayer && cells[1][1] == currentPlayer && cells[2][0] == currentPlayer) {
                return true;
            }
        }
        return false;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == Player.X) ? Player.O : Player.X;
    }

    private boolean isValidCoordinate(int c) {
        return c >= 0 && c < 3;
    }
}