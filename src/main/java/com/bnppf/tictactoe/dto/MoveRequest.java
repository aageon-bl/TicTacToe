// src/main/java/com/bnppf/tictactoe/dto/MoveRequest.java
package com.bnppf.tictactoe.dto;

public class MoveRequest {
    private int row;
    private int col;
    // private Player player; // Optional: if you want client to specify player,
    // otherwise server determines by currentPlayer

    public int getRow() { return row; }
    public void setRow(int row) { this.row = row; }
    public int getCol() { return col; }
    public void setCol(int col) { this.col = col; }
}