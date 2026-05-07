package com.example.demo.Models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "saved_healthcare")
@Data
public class SavedHealthcare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "healthcare_service_id", nullable = false)
    private Healthcare healthcare;

    private Boolean isRead = false;

    // Added field for PUT functionality
    private String note;
}
