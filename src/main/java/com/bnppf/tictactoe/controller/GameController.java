// src/main/java/com/bnppf/tictactoe/controller/GameController.java
package com.bnppf.tictactoe.controller;

import com.bnppf.tictactoe.domain.Board;
import com.bnppf.tictactoe.dto.GameResponse;
import com.bnppf.tictactoe.dto.MoveRequest;
import com.bnppf.tictactoe.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game") // Base path for all game-related endpoints
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/start")
    public ResponseEntity<GameResponse> startNewGame() {
        Board board = gameService.startNewGame();
        return ResponseEntity.ok(createGameResponse(board, "New game started. Player " + board.getCurrentPlayer() + "'s turn."));
    }

    @PostMapping("/move")
    public ResponseEntity<GameResponse> makeMove(@RequestBody MoveRequest moveRequest) {
        // Player info could be part of MoveRequest or derived if needed
        Board board = gameService.makeMove(moveRequest.getRow(), moveRequest.getCol());
        String message = determineMessage(board);
        return ResponseEntity.ok(createGameResponse(board, message));
    }

    @GetMapping
    public ResponseEntity<GameResponse> getGame() {
        Board board = gameService.getGameBoard();
        String message = determineMessage(board);
        return ResponseEntity.ok(createGameResponse(board, message));
    }

    private GameResponse createGameResponse(Board board, String message) {
        return new GameResponse(
                board.getCells(), // Make sure Board.getCells() returns a representation suitable for JSON
                board.getCurrentPlayer(),
                board.getGameStatus(),
                message
        );
    }

    private String determineMessage(Board board) {
        switch (board.getGameStatus()) {
            case X_WINS: return "Player X wins!";
            case O_WINS: return "Player O wins!";
            case DRAW: return "It's a draw!";
            case IN_PROGRESS: return "Player " + board.getCurrentPlayer() + "'s turn.";
            default: return "Game status: " + board.getGameStatus();
        }
    }
}