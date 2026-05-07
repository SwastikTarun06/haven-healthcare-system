package com.example.demo.Repos;

import com.example.demo.Models.Healthcare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HealthcareRepository extends JpaRepository<Healthcare, Integer> {
    List<Healthcare> findByService_Postcode(String postcode);
    List<Healthcare> findByEmergencySupportTrue();
}