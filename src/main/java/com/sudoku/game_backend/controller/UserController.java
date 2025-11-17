package com.sudoku.game_backend.controller;

import com.sudoku.game_backend.model.Difficulty;
import com.sudoku.game_backend.model.User;
import com.sudoku.game_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String password = request.get("password");
            String email = request.get("email");
            
            User user = userService.registerUser(username, password, email);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Login user
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String password = request.get("password");
            
            User user = userService.loginUser(username, password);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // Get user profile
    @GetMapping("/profile/{username}")
    public ResponseEntity<?> getProfile(@PathVariable String username) {
        try {
            User user = userService.getUserProfile(username);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Update user's score after completing a game
    @PostMapping("/score")
    public ResponseEntity<?> updateScore(@RequestBody Map<String, Object> request) {
        try {
            String username = (String) request.get("username");
            Integer score = (Integer) request.get("score");
            String difficultyStr = (String) request.get("difficulty");
            Integer timeTaken = (Integer) request.get("timeTaken");
            
            Difficulty difficulty = Difficulty.valueOf(difficultyStr.toUpperCase());
            
            User user = userService.updateScore(username, score, difficulty, timeTaken);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}