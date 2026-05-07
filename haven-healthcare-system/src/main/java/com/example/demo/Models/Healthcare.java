package com.example.demo.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "healthcare_details")
@Data
public class Healthcare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "service_id", nullable = false)
    @JsonIgnoreProperties("healthcare") // Prevents infinite recursion during JSON serialization
    private Service service; // Linked to the shared Service class in the main branch

    private String healthcareType;
    private Double rating;
    private Integer reviewsCount;
    
    @Column(columnDefinition = "TEXT")
    private String extraDescription;
    
    private Boolean isFree;
    private Boolean isLowCost;
    private Boolean emergencySupport;
}
