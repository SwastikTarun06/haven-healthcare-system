package com.example.demo.Models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "shelter_details")
@Data
public class ShelterDetails {
    @Id
    private Integer id; 

    @OneToOne
    @MapsId
    @JoinColumn(name = "service_id")
    private Service service;

    private Integer currentBedCount; 
    private Integer totalCapacity;
    private Boolean isPetFriendly;
    private Boolean isAccessible;
    private Boolean isOpen;
    private Boolean allowsFamilies;
}