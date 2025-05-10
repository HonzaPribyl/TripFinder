package cz.sa.tripfinder.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TripPureDTO {
    private Long id;
    private float price;
    private String location;
    private Long locationId;
    private String hotel;
    private int stars;
    private String foodPackage;
    private String airport;
    private Long airportId;
    private String beachDist;
    private boolean familyFriendly;
    private boolean internet;
    private boolean swimmingPool;
    private float averageRating;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private int days;
}
