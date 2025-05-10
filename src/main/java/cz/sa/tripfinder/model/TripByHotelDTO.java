package cz.sa.tripfinder.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TripByHotelDTO {
    private Long id;
    private float price;
    private String location;
    private String hotel;
    private int stars;
    private String foodPackage;
    private String airport;
    private String beachDist;
    private boolean familyFriendly;
    private boolean internet;
    private boolean swimmingPool;
    private float averageRating;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private int days;
    private float priceScore;
    private float foodScore;
    private float airportScore;
    private float totalScore;
}
