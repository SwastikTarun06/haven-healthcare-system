package com.example.demo.Models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "services")
@Data
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String type; 
    private String address;
    private String postcode;
    private String status;
    private Double latitude; 
    private Double longitude;

    // --- NEW FIELDS FOR YOUR "DETAILS" PAGE ---
    private String phoneNumber;
    private String emailContact;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    private String openingHours; // e.g., "09:00 - 21:00"
    private String imageUrl;     // Link to a shelter photo
    
    @Column(columnDefinition = "TEXT")
    private String policies;     // e.g., "No alcohol, Must be over 18"

    // This links the service to a User (Admin/Staff) in your ERD
    @ManyToOne
    @JoinColumn(name = "managed_by_id")
    private User managedBy;
}