package com.sudoku.game_backend.service;

import com.sudoku.game_backend.model.Difficulty;
import com.sudoku.game_backend.model.GameScore;
import com.sudoku.game_backend.model.User;
import com.sudoku.game_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    // Register new user
    public User registerUser(String username, String password, String email) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        User user = new User(username, password, email);
        return userRepository.save(user);
    }
    
    // Login user
    public User loginUser(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        
        User user = userOpt.get();
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid password");
        }
        
        user.setLastLogin(LocalDateTime.now());
        return userRepository.save(user);
    }
    
    // Get user profile
    public User getUserProfile(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
    
    // Update score after game completion
    public User updateScore(String username, int score, Difficulty difficulty, long timeTaken) {
        User user = getUserProfile(username);
        
        // Add new score to history
        GameScore gameScore = new GameScore(score, difficulty.name(), timeTaken, LocalDateTime.now());
        user.getScores().add(gameScore);
        
        // Update max score if this is higher
        if (score > user.getMaxScore()) {
            user.setMaxScore(score);
        }
        
        user.setGamesPlayed(user.getGamesPlayed() + 1);
        
        return userRepository.save(user);
    }
}