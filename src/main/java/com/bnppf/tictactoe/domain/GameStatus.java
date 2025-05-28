// src/main/java/com/bnppf/tictactoe/domain/GameStatus.java
package com.bnppf.tictactoe.domain;

public enum GameStatus {
    IN_PROGRESS,
    X_WINS,
    O_WINS,
    DRAW,
    NOT_STARTED // Optional: useful for initial state
}