package com.sudoku.game_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SudokuBoard {
    private int[][] board;
    private int[][] solution;
    
    public SudokuBoard(int[][] board) {
        this.board = board;
    }
}