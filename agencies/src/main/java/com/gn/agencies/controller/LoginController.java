package com.gn.agencies.controller;

import com.gn.agencies.entity.Admin;
import com.gn.agencies.entity.Customer;
import com.gn.agencies.repository.AdminRepository;
import com.gn.agencies.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from React frontend
public class LoginController {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @PostMapping
    public String login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");
        Admin foundAdmin = adminRepository.findByUsernameAndPassword(username, password);
        if (foundAdmin != null) {
            System.out.println("Admin login successful: " + foundAdmin);
            return "admin";
        }

        // Check if the user is a Customer
        Customer foundCustomer = customerRepository.findByLoginIdAndPassword(username, password);
        if (foundCustomer != null) {
            System.out.println("Customer login successful: " + foundCustomer);
            return "customer";
        }

        // If neither Admin nor Customer is found
        return "Invalid credentials";
    }

}

