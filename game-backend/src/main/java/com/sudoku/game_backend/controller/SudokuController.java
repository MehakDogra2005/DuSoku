package com.sudoku.game_backend.controller;

import com.sudoku.game_backend.model.Difficulty;
import com.sudoku.game_backend.model.SudokuBoard;
import com.sudoku.game_backend.service.SudokuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sudoku")
@CrossOrigin(origins = "*")
public class SudokuController {
    
    @Autowired
    private SudokuService sudokuService;
    
    // Endpoint: GET /api/sudoku/generate?difficulty=easy
    @GetMapping("/generate")
    public ResponseEntity<SudokuBoard> generatePuzzle(@RequestParam String difficulty) {
        try {
            Difficulty diff = Difficulty.valueOf(difficulty.toUpperCase());
            SudokuBoard puzzle = sudokuService.generatePuzzle(diff);
            return ResponseEntity.ok(puzzle);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // Endpoint: POST /api/sudoku/solve
    @PostMapping("/solve")
    public ResponseEntity<int[][]> solvePuzzle(@RequestBody int[][] board) {
        int[][] solution = sudokuService.solvePuzzle(board);
        return ResponseEntity.ok(solution);
    }
    
    // Endpoint: POST /api/sudoku/validate
    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateSolution(@RequestBody int[][] board) {
        boolean isValid = sudokuService.validateSolution(board);
        return ResponseEntity.ok(isValid);
    }
    
    // Endpoint: POST /api/sudoku/hint
    @PostMapping("/hint")
    public ResponseEntity<int[]> getHint(@RequestBody int[][] board) {
        int[] hint = sudokuService.getHint(board);
        if (hint == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(hint);
    }
}