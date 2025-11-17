package com.sudoku.game_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameScore {
    private int score;
    private String difficulty;
    private long timeTaken; // in seconds
    private LocalDateTime completedAt;
}