package com.example.demo.Repos;

import com.example.demo.Models.ShelterReferral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShelterReferralRepository extends JpaRepository<ShelterReferral, Integer> {
}