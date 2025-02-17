package com.gn.agencies.controller;

import com.gn.agencies.DTO.CustomerLocationDTO;
import com.gn.agencies.entity.Customer;
import com.gn.agencies.entity.CustomerLocation;
import com.gn.agencies.repository.CustomerLocationRepository;
import com.gn.agencies.repository.CustomerRepository;
import com.gn.agencies.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // Allow React app origin
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    // Fetch logged-in customer details by loginId
    @GetMapping("/profile")
    public ResponseEntity<Customer> getCustomerProfile(@RequestParam("loginId") String loginId) {
        Customer customer = customerRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("Customer not found with loginId: " + loginId));
        return ResponseEntity.ok(customer);
    }

    // Fetch customer details by ID
    @GetMapping("/profilebyid")
    public ResponseEntity<Customer> getCustomerProfileById(@RequestParam("id") Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id));
        return ResponseEntity.ok(customer);
    }

    // Fetch all customers
    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    // Save or update customer details
    @PostMapping
    public ResponseEntity<Customer> saveOrUpdateCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.saveCustomer(customer);
        return ResponseEntity.ok(savedCustomer);
    }

    // Delete a customer by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        try {
            customerService.deleteCustomer(id);
            return ResponseEntity.ok("Customer deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete customer: " + e.getMessage());
        }
    }

    // Fetch paginated customer results
    @GetMapping("/paginated")
    public ResponseEntity<List<Customer>> getPaginatedCustomers(
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        List<Customer> customers = customerService.getPaginatedCustomers(page, size);
        return ResponseEntity.ok(customers);
    }

    // Search customers by name
    @GetMapping("/search")
    public ResponseEntity<List<Customer>> searchCustomersByName(@RequestParam("name") String name) {
        List<Customer> customers = customerService.searchCustomersByName(name);
        return ResponseEntity.ok(customers);
    }

    @Autowired
    private CustomerLocationRepository locationRepository;

    @PostMapping("/location")
    public ResponseEntity<String> saveCustomerLocation(@RequestBody CustomerLocationDTO locationDTO) {
        try {
            Customer customer = customerRepository.findById(locationDTO.getCustomerId())
                    .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

            CustomerLocation location = new CustomerLocation();
            location.setCustomer(customer);
            location.setLatitude(locationDTO.getLatitude());
            location.setLongitude(locationDTO.getLongitude());
            locationRepository.save(location);

            return ResponseEntity.ok("Location saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving location: " + e.getMessage());
        }
    }
}

