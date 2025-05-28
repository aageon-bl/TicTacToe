// src/test/java/com/bnppf/tictactoe/controller/GameControllerTest.java
package com.bnppf.tictactoe.controller;

import com.bnppf.tictactoe.domain.Board;
import com.bnppf.tictactoe.domain.GameStatus;
import com.bnppf.tictactoe.domain.Player;
import com.bnppf.tictactoe.dto.MoveRequest;
import com.bnppf.tictactoe.exception.InvalidMoveException;
import com.bnppf.tictactoe.service.GameService;
import com.fasterxml.jackson.databind.ObjectMapper; // For converting objects to JSON
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GameController.class) // Test only the GameController, not full app context
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc; // For making mock HTTP requests

    @MockBean // Creates a Mock of GameService, as we don't want to test its logic here
    private GameService gameService;

    @Autowired
    private ObjectMapper objectMapper; // For JSON conversion

    @Test
    void startNewGame_shouldReturnInitialBoardState() throws Exception {
        Board mockBoard = new Board(); // Assume Board constructor sets up initial state
        when(gameService.startNewGame()).thenReturn(mockBoard);

        mockMvc.perform(post("/api/game/start"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameStatus").value(GameStatus.IN_PROGRESS.toString()))
                .andExpect(jsonPath("$.currentPlayer").value(Player.X.toString()))
                .andExpect(jsonPath("$.board").isArray()); // Check if board is present
    }

    @Test
    void makeMove_validMove_shouldReturnUpdatedBoardState() throws Exception {
        MoveRequest moveRequest = new MoveRequest();
        moveRequest.setRow(0);
        moveRequest.setCol(0);

        Board updatedBoard = new Board(); // Create a board that reflects the move
        updatedBoard.makeMove(0,0); // Simulate the move

        when(gameService.makeMove(0, 0)).thenReturn(updatedBoard);

        mockMvc.perform(post("/api/game/move")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(moveRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.board[0][0]").value(Player.X.toString()))
                .andExpect(jsonPath("$.currentPlayer").value(Player.O.toString()));
    }

    @Test
    void makeMove_invalidMove_shouldReturnBadRequest() throws Exception {
        MoveRequest moveRequest = new MoveRequest();
        moveRequest.setRow(0);
        moveRequest.setCol(0);

        when(gameService.makeMove(anyInt(), anyInt())).thenThrow(new InvalidMoveException("Cell occupied"));

        mockMvc.perform(post("/api/game/move")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(moveRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Cell occupied")); // Assuming you'll add a @ControllerAdvice
    }


    @Test
    void getGame_shouldReturnCurrentBoardState() throws Exception {
        Board currentBoard = new Board();
        currentBoard.makeMove(1,1); // O's turn after X
        when(gameService.getGameBoard()).thenReturn(currentBoard);

        mockMvc.perform(get("/api/game"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPlayer").value(Player.O.toString()))
                .andExpect(jsonPath("$.board[1][1]").value(Player.X.toString()));
    }
}