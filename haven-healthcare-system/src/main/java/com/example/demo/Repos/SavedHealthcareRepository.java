package com.example.demo.Repos;

import com.example.demo.Models.SavedHealthcare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavedHealthcareRepository extends JpaRepository<SavedHealthcare, Integer> {
    //  Method to check if a specific user has already saved a specific clinic
    boolean existsByUserIdAndHealthcareId(Integer userId, Integer healthcareId);
}
