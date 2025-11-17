package com.sudoku.game_backend.service;

import com.sudoku.game_backend.model.Difficulty;
import com.sudoku.game_backend.model.SudokuBoard;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SudokuService {
    
    private final Random random = new Random();
    
    // Main method to generate a puzzle
    public SudokuBoard generatePuzzle(Difficulty difficulty) {
        int[][] board = new int[9][9];
        
        fillDiagonalBoxes(board);
        solve(board);
        
        int[][] solution = copyBoard(board);
        removeNumbers(board, difficulty.getEmptyCells());
        
        return new SudokuBoard(board, solution);
    }
    
    // Solve any given puzzle
    public int[][] solvePuzzle(int[][] board) {
        int[][] boardCopy = copyBoard(board);
        solve(boardCopy);
        return boardCopy;
    }
    
    // Validate if solution is correct
    public boolean validateSolution(int[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int num = board[i][j];
                board[i][j] = 0;
                if (!isValid(board, i, j, num)) {
                    board[i][j] = num;
                    return false;
                }
                board[i][j] = num;
            }
        }
        
        return true;
    }
    
    // Get hint for the user - returns [row, col, correct_number]
    public int[] getHint(int[][] board) {
        // Find the solution first
        int[][] solutionBoard = copyBoard(board);
        solve(solutionBoard);
        
        // Find an empty cell and return its correct value
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    // Return [row, col, correct_number]
                    return new int[]{i, j, solutionBoard[i][j]};
                }
            }
        }
        
        return null; // No empty cells (puzzle completed)
    }
    
    // BACKTRACKING ALGORITHM - This is the key solving logic
    private boolean solve(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValid(board, row, col, num)) {
                            board[row][col] = num;
                            
                            if (solve(board)) {
                                return true;
                            }
                            
                            board[row][col] = 0; // Backtrack
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
    
    // Check if placing a number is valid
    private boolean isValid(int[][] board, int row, int col, int num) {
        // Check row
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num) {
                return false;
            }
        }
        
        // Check column
        for (int i = 0; i < 9; i++) {
            if (board[i][col] == num) {
                return false;
            }
        }
        
        // Check 3x3 box
        int boxRow = row - row % 3;
        int boxCol = col - col % 3;
        
        for (int i = boxRow; i < boxRow + 3; i++) {
            for (int j = boxCol; j < boxCol + 3; j++) {
                if (board[i][j] == num) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    // Fill the three diagonal 3x3 boxes
    private void fillDiagonalBoxes(int[][] board) {
        for (int box = 0; box < 9; box += 3) {
            fillBox(board, box, box);
        }
    }
    
    // Fill a single 3x3 box
    private void fillBox(int[][] board, int row, int col) {
        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        shuffleArray(nums);
        
        int index = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[row + i][col + j] = nums[index++];
            }
        }
    }
    
    // Remove numbers to create puzzle
    private void removeNumbers(int[][] board, int count) {
        int removed = 0;
        while (removed < count) {
            int row = random.nextInt(9);
            int col = random.nextInt(9);
            
            if (board[row][col] != 0) {
                board[row][col] = 0;
                removed++;
            }
        }
    }
    
    // Copy board
    private int[][] copyBoard(int[][] board) {
        int[][] copy = new int[9][9];
        for (int i = 0; i < 9; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, 9);
        }
        return copy;
    }
    
    // Shuffle array
    private void shuffleArray(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
}