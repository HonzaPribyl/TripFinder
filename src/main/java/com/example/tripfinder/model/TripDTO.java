package com.example.tripfinder.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TripDTO {
    private Long id;
    private float price;
    private int score;
}
