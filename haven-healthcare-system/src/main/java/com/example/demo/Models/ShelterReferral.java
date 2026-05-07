package com.example.demo.Models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "shelter_referrals")
@Data
public class ShelterReferral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "from_service_id")
    private Service fromService;

    @ManyToOne
    @JoinColumn(name = "to_service_id")
    private Service toService;

    // Fixed: Standardized to 'seeker' object but links to 'seeker_id' column
    @ManyToOne
    @JoinColumn(name = "seeker_id")
    private User seeker; 

    private String status;     

    // Fixed: Standardized to 'createdAt' but links to 'created_at' column
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}