package com.gn.agencies.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Feedbackdao {
    private int id;
    private String customerName;
    private String customerEmail;
    private String feedbackText;
    private Timestamp createdAt;

    // Getters and setters
}
