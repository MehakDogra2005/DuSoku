package com.sudoku.game_backend.model;

public enum Difficulty {
    EASY(40),
    MEDIUM(50),
    HARD(55),
    EXPERT(60);
    
    private final int emptyCells;
    
    Difficulty(int emptyCells) {
        this.emptyCells = emptyCells;
    }
    
    public int getEmptyCells() {
        return emptyCells;
    }
}