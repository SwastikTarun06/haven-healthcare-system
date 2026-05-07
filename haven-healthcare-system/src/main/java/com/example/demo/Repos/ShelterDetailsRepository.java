package com.example.demo.Repos;

import com.example.demo.Models.ShelterDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ShelterDetailsRepository extends JpaRepository<ShelterDetails, Integer> {
    
    // Spring looks for 'isPetFriendly'
    List<ShelterDetails> findByIsPetFriendlyTrue();

    // Spring looks for 'currentBedCount'
    List<ShelterDetails> findByCurrentBedCountGreaterThan(Integer count);

    // Spring looks inside 'Service' for 'postcode'
    List<ShelterDetails> findByService_Postcode(String postcode);
}