package com.example.tripfinder.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TripDTO {
    private Long id;
    private float price;
    private String hotel;
    private String beachDist;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private int score;
}
