// src/test/java/com/bnppf/tictactoe/domain/BoardTest.java
package com.bnppf.tictactoe.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;

    @BeforeEach // This method runs before each test
    void setUp() {
        board = new Board(); // We'll create the Board class next
    }

    @Test
    void newBoardShouldBeEmptyAndPlayerXToStart() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertNull(board.getCell(i, j), "Cell (" + i + "," + j + ") should be empty.");
            }
        }
        assertEquals(Player.X, board.getCurrentPlayer(), "Player X should be the starting player.");
        assertEquals(GameStatus.IN_PROGRESS, board.getGameStatus(), "Game should be in progress initially."); // Or NOT_STARTED if you prefer
    }

    @Test
    void makeMove_shouldPlaceMarkAndSwitchPlayer() {
        assertTrue(board.makeMove(0, 0), "Move should be successful.");
        assertEquals(Player.X, board.getCell(0, 0), "Cell (0,0) should contain X.");
        assertEquals(Player.O, board.getCurrentPlayer(), "Player should switch to O.");
        assertEquals(GameStatus.IN_PROGRESS, board.getGameStatus());
    }

    @Test
    void makeMove_onOccupiedCell_shouldFail() {
        board.makeMove(0, 0); // Player X moves
        assertFalse(board.makeMove(0, 0), "Move on occupied cell should fail."); // Player O tries same cell
        assertEquals(Player.X, board.getCell(0, 0), "Cell (0,0) should still contain X.");
        assertEquals(Player.O, board.getCurrentPlayer(), "Player should remain O as the move failed.");
    }

    @Test
    void makeMove_outOfBounds_shouldFail() {
        assertFalse(board.makeMove(3, 0), "Move out of bounds (row) should fail.");
        assertFalse(board.makeMove(0, 3), "Move out of bounds (col) should fail.");
        assertEquals(Player.X, board.getCurrentPlayer(), "Player should remain X as the move failed.");
    }
    @Test
    void makeMove_playerXWinsHorizontally() {
        board.makeMove(0, 0); // X
        board.makeMove(1, 0); // O
        board.makeMove(0, 1); // X
        board.makeMove(1, 1); // O
        assertTrue(board.makeMove(0, 2), "X makes winning move."); // X
        assertEquals(GameStatus.X_WINS, board.getGameStatus());
        assertEquals(Player.X, board.getCell(0,2));
    }

    @Test
    void makeMove_playerOWinsVertically() {
        board.makeMove(0, 0); // X
        board.makeMove(0, 1); // O
        board.makeMove(1, 0); // X
        board.makeMove(1, 1); // O
        board.makeMove(2, 2); // X
        assertTrue(board.makeMove(2, 1), "O makes winning move."); // O
        assertEquals(GameStatus.O_WINS, board.getGameStatus());
    }

    @Test
    void makeMove_playerXWinsDiagonallyFromTopLeft() {
        board.makeMove(0, 0); // X
        board.makeMove(0, 1); // O
        board.makeMove(1, 1); // X
        board.makeMove(0, 2); // O
        assertTrue(board.makeMove(2, 2), "X makes winning move."); // X
        assertEquals(GameStatus.X_WINS, board.getGameStatus());
    }


    @Test
    void makeMove_cannotMoveAfterGameWon() {
        // X wins
        board.makeMove(0, 0); // X
        board.makeMove(1, 0); // O
        board.makeMove(0, 1); // X
        board.makeMove(1, 1); // O
        board.makeMove(0, 2); // X wins

        assertEquals(GameStatus.X_WINS, board.getGameStatus());
        // Current player would be O, but game is over
        assertFalse(board.makeMove(2,0), "Should not be able to move after game is won.");
        assertNull(board.getCell(2,0)); // Cell should remain empty
    }
    @Test
    void makeMove_shouldResultInDraw() {
        // X O X
        // X X O
        // O X O
        board.makeMove(0, 0); // X
        board.makeMove(0, 1); // O
        board.makeMove(0, 2); // X

        board.makeMove(1, 2); // O
        board.makeMove(1, 0); // X
        board.makeMove(2, 0); // O

        board.makeMove(1, 1); // X
        board.makeMove(2, 2); // O
        assertTrue(board.makeMove(2, 1), "X makes the final move for a draw."); // X

        assertEquals(GameStatus.DRAW, board.getGameStatus());
    }
}