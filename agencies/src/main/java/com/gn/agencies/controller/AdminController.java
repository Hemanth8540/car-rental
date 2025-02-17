package com.gn.agencies.controller;

import com.gn.agencies.entity.Admin;
import com.gn.agencies.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // Allow React app origin
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    // Fetch admin profile by username (loginId)
    @GetMapping("/profile")
    public ResponseEntity<Admin> getAdminProfile(@RequestParam("loginId") String loginId) {
        Admin admin = adminRepository.findByUsername(loginId);
        if (admin == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)                                                                                                                                                                                                                                                                                                                                                                                                                                 
                    .body(null); // Return 404 if admin not found
        }
        return ResponseEntity.ok(admin);
    }

    // Update admin profile
    @PostMapping
    public ResponseEntity<Admin> updateAdminProfile(@RequestBody Admin admin) {
        try {
            Admin updatedAdmin = adminRepository.save(admin);
            return ResponseEntity.ok(updatedAdmin); // Return updated admin details
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // Return 500 if there's an error
        }
    }
}
