// src/main/java/com/bnppf/tictactoe/dto/GameResponse.java
package com.bnppf.tictactoe.dto;

import com.bnppf.tictactoe.domain.Player;
import com.bnppf.tictactoe.domain.GameStatus;

public class GameResponse {
    private Player[][] board; // Represent as String[][] or Player[][]
    private Player currentPlayer;
    private GameStatus gameStatus;
    private String message; // Optional: for messages like "X's turn" or "Game Over"

    public GameResponse(Player[][] board, Player currentPlayer, GameStatus gameStatus, String message) {
        this.board = board;
        this.currentPlayer = currentPlayer;
        this.gameStatus = gameStatus;
        this.message = message;
    }
    
    public Player[][] getBoard() { return board; }
    public void setBoard(Player[][] board) { this.board = board; }
    public Player getCurrentPlayer() { return currentPlayer; }
    public void setCurrentPlayer(Player currentPlayer) { this.currentPlayer = currentPlayer; }
    public GameStatus getGameStatus() { return gameStatus; }
    public void setGameStatus(GameStatus gameStatus) { this.gameStatus = gameStatus; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}