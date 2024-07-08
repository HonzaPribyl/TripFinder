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
    private int stars;
    private String foodPackage;
    private String airport;
    private String beachDist;
    private float averageRating;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private float priceScore;
    private float starsScore;
    private float foodScore;
    private float ratingScore;
    private float beachDistScore;
    private float totalScore;
}
