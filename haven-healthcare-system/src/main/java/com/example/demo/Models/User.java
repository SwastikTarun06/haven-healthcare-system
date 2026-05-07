package com.example.demo.Models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    private String role; // e.g., "STAFF", "ADMIN", "SEEKER"
    
    // Added these to match your SQL inserts
    private String email;
    private String name;

    @CreationTimestamp // Automatically sets the time when the user is created
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}