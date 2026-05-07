package com.example.demo.Controllers;

import com.example.demo.Models.*;
import com.example.demo.Repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/shelters")
public class ShelterController {

    @Autowired
    private ServiceRepository serviceRepo;

    @Autowired
    private ShelterDetailsRepository shelterDetailsRepo;

    @Autowired
    private ShelterReferralRepository referralRepo;

    // --- SEEKER PERSPECTIVE ---

    @GetMapping
    public List<ShelterDetails> getAllShelters() {
        return shelterDetailsRepo.findAll();
    }

    // New: Specific endpoint for Map Pins (Returns only essential location data)
    @GetMapping("/map-pins")
    public List<Service> getMapPins() {
        return serviceRepo.findAll(); // This allows the map to show Food, Jobs, and Health too
    }

    @GetMapping("/search")
    public List<ShelterDetails> searchByPostcode(@RequestParam String postcode) {
        return shelterDetailsRepo.findByService_Postcode(postcode);
    }

    @GetMapping("/pet-friendly")
    public List<ShelterDetails> getPetFriendly() {
        return shelterDetailsRepo.findByIsPetFriendlyTrue();
    }

    @GetMapping("/available")
    public List<ShelterDetails> getAvailable() {
        return shelterDetailsRepo.findByCurrentBedCountGreaterThan(0);
    }

    // --- SERVICE PROVIDER PERSPECTIVE (Dashboard Actions) ---

    // 1. Update LIVE Shelter Status (Beds, Open/Closed)
    @PutMapping("/{id}/details")
    public ShelterDetails updateShelterDetails(@PathVariable Integer id, @RequestBody ShelterDetails updatedDetails) {
        ShelterDetails existing = shelterDetailsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Shelter Details not found for ID: " + id));

        existing.setCurrentBedCount(updatedDetails.getCurrentBedCount());
        existing.setTotalCapacity(updatedDetails.getTotalCapacity());
        existing.setIsOpen(updatedDetails.getIsOpen());
        existing.setIsPetFriendly(updatedDetails.getIsPetFriendly());
        existing.setAllowsFamilies(updatedDetails.getAllowsFamilies());

        return shelterDetailsRepo.save(existing);
    }

    // 2. Update STATIC Shelter Info (Contact, Policies, Description)
    @PutMapping("/service/{id}")
    public Service updateServiceInfo(@PathVariable Integer id, @RequestBody Service updatedService) {
        Service existing = serviceRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found for ID: " + id));

        existing.setPhoneNumber(updatedService.getPhoneNumber());
        existing.setEmailContact(updatedService.getEmailContact());
        existing.setDescription(updatedService.getDescription());
        existing.setOpeningHours(updatedService.getOpeningHours());
        existing.setPolicies(updatedService.getPolicies());
        existing.setImageUrl(updatedService.getImageUrl());

        return serviceRepo.save(existing);
    }

    // --- REFERRAL LOGIC ---

    @PostMapping("/referral")
    public ShelterReferral createReferral(@RequestBody ShelterReferral referral) {
        return referralRepo.save(referral);
    }

    @PutMapping("/referral/{id}/accept")
    public String acceptReferral(@PathVariable Integer id) {
        ShelterReferral referral = referralRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Referral not found"));
        
        referral.setStatus("ACCEPTED");
        referralRepo.save(referral);

        // Deduct bed from the TARGET shelter
        ShelterDetails details = shelterDetailsRepo.findById(referral.getToService().getId())
                .orElseThrow(() -> new RuntimeException("Target shelter details not found"));
        
        if (details.getCurrentBedCount() > 0) {
            details.setCurrentBedCount(details.getCurrentBedCount() - 1);
            shelterDetailsRepo.save(details);
            return "Referral Accepted. Bed count reduced to: " + details.getCurrentBedCount();
        }
        return "No beds available!";
    }
}