package com.example.demo.Controllers;

import com.example.demo.Models.*;
import com.example.demo.Repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;          
import org.springframework.data.domain.PageRequest;   
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/healthcare")
public class HealthcareController {

    @Autowired
    private HealthcareRepository healthcareRepo;
    
    @Autowired
    private SavedHealthcareRepository savedRepo;

    @GetMapping
    public List<Healthcare> getAllHealthcare() {
        return healthcareRepo.findAll();
    }

    @GetMapping("/saved/{userId}")
    public List<SavedHealthcare> getSavedForUser(@PathVariable Integer userId) {
        return savedRepo.findAll().stream()
                .filter(s -> s.getUser().getId().equals(userId))
                .collect(Collectors.toList());
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveForUser(@RequestBody SavedHealthcare request) {
        Integer uId = request.getUser().getId();
        Integer hId = request.getHealthcare().getId();

        if (!healthcareRepo.existsById(hId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Healthcare facility not found.");
        }

        if (savedRepo.existsByUserIdAndHealthcareId(uId, hId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Clinic is already saved to your dashboard.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(savedRepo.save(request));
    }

    // PUT: endpoint to update a saved record
    @PutMapping("/saved/{id}")
    public ResponseEntity<?> updateSavedClinic(@PathVariable Integer id, @RequestBody SavedHealthcare updateDetails) {
        Optional<SavedHealthcare> existingRecord = savedRepo.findById(id);

        if (existingRecord.isPresent()) {
            SavedHealthcare record = existingRecord.get();
            record.setNote(updateDetails.getNote());
            record.setIsRead(updateDetails.getIsRead());
            SavedHealthcare updated = savedRepo.save(record);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Record not found.");
        }
    }

    @DeleteMapping("/saved/{id}")
    public ResponseEntity<?> unsaveClinic(@PathVariable Integer id) {
        if (savedRepo.existsById(id)) {
            savedRepo.deleteById(id);
            return ResponseEntity.ok().body("Successfully removed from saved list.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Record not found.");
    }

    @GetMapping("/paged")
    public Page<Healthcare> getHealthcarePaged(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        return healthcareRepo.findAll(PageRequest.of(page, size));
    }
}