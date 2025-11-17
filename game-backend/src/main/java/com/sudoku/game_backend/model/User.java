package com.sudoku.game_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String username;
    
    private String password; // In production, this should be hashed
    
    private String email;
    
    private int maxScore;
    
    private int gamesPlayed;
    
    private List<GameScore> scores;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime lastLogin;
    
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.maxScore = 0;
        this.gamesPlayed = 0;
        this.scores = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
    }
}