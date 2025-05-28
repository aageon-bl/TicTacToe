// src/main/java/com/bnppf/tictactoe/service/GameService.java
package com.bnppf.tictactoe.service;

import com.bnppf.tictactoe.domain.Board;
import com.bnppf.tictactoe.domain.GameStatus;
import com.bnppf.tictactoe.domain.Player;
import com.bnppf.tictactoe.exception.InvalidMoveException; // Create this exception later
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private Board board; // Manages a single game instance

    public GameService() {
        startNewGame(); // Initialize a game when service is created
    }

    public Board startNewGame() {
        this.board = new Board();
        return this.board;
    }

    public Board makeMove(int row, int col) {
        if (board.getGameStatus() != GameStatus.IN_PROGRESS) {
            throw new InvalidMoveException("Game is already over. Please start a new game.");
        }
        // We could also check whose turn it is here and throw if it's not the player sent from UI
        // For now, the board's currentPlayer handles turns implicitly.

        boolean moveMade = board.makeMove(row, col);
        if (!moveMade) {
            // Determine why the move was invalid
            if (row < 0 || row > 2 || col < 0 || col > 2) {
                throw new InvalidMoveException("Move is outside the board boundaries.");
            } else if (board.getCell(row, col) != null) {
                throw new InvalidMoveException("Cell (" + row + "," + col + ") is already occupied.");
            } else {
                throw new InvalidMoveException("Invalid move. Reason unknown or game already over.");
            }
        }
        return board;
    }

    public Board getGameBoard() {
        return board;
    }

    public GameStatus getGameStatus() {
        return board.getGameStatus();
    }

    public Player getCurrentPlayer() {
        return board.getCurrentPlayer();
    }
}