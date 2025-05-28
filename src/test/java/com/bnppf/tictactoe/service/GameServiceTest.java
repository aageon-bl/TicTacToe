// src/test/java/com/bnppf/tictactoe/service/GameServiceTest.java
package com.bnppf.tictactoe.service;

import com.bnppf.tictactoe.domain.Board;
import com.bnppf.tictactoe.domain.GameStatus;
import com.bnppf.tictactoe.domain.Player;
import com.bnppf.tictactoe.exception.InvalidMoveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    private GameService gameService;

    @BeforeEach
    void setUp() {
        gameService = new GameService();
    }

    @Test
    void startNewGame_shouldReturnInitializedBoard() {
        Board board = gameService.startNewGame();
        assertNotNull(board);
        assertEquals(GameStatus.IN_PROGRESS, board.getGameStatus());
        assertEquals(Player.X, board.getCurrentPlayer());
    }

    @Test
    void makeMove_validMove_shouldUpdateBoard() {
        Board board = gameService.makeMove(0, 0);
        assertEquals(Player.X, board.getCell(0, 0));
        assertEquals(Player.O, board.getCurrentPlayer());
    }

    @Test
    void makeMove_onOccupiedCell_shouldThrowInvalidMoveException() {
        gameService.makeMove(0, 0); // X moves
        Exception exception = assertThrows(InvalidMoveException.class, () -> {
            gameService.makeMove(0, 0); // O tries same cell
        });
        assertEquals("Cell (0,0) is already occupied.", exception.getMessage());
    }

    @Test
    void makeMove_outOfBounds_shouldThrowInvalidMoveException() {
        Exception exception = assertThrows(InvalidMoveException.class, () -> {
            gameService.makeMove(3, 0);
        });
        assertTrue(exception.getMessage().contains("outside the board boundaries"));
    }

    @Test
    void makeMove_whenGameIsOver_shouldThrowInvalidMoveException() {
        // Simulate a win for X
        gameService.makeMove(0,0); // X
        gameService.makeMove(1,0); // O
        gameService.makeMove(0,1); // X
        gameService.makeMove(1,1); // O
        gameService.makeMove(0,2); // X wins

        assertEquals(GameStatus.X_WINS, gameService.getGameStatus());

        Exception exception = assertThrows(InvalidMoveException.class, () -> {
            gameService.makeMove(2,0); // Try another move
        });
        assertEquals("Game is already over. Please start a new game.", exception.getMessage());
    }

    @Test
    void getGameBoard_shouldReturnCurrentBoard() {
        gameService.makeMove(0,0);
        Board board = gameService.getGameBoard();
        assertEquals(Player.X, board.getCell(0,0));
    }

    @Test
    void getGameStatus_shouldReturnCurrentStatus() {
        assertEquals(GameStatus.IN_PROGRESS, gameService.getGameStatus());
        gameService.makeMove(0,0); // X
        gameService.makeMove(1,0); // O
        gameService.makeMove(0,1); // X
        gameService.makeMove(1,1); // O
        gameService.makeMove(0,2); // X wins
        assertEquals(GameStatus.X_WINS, gameService.getGameStatus());
    }
}