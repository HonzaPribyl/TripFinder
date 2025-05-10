package cz.sa.tripfinder.model;

import jakarta.annotation.Nonnull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TripFuzzyDTO {

    private Long id;
    private float price;
    private String location;
    private Long hotelId;
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
    private double ratingAttributesScore;
    private double equipmentScore;
    private double hotelScore;
    private double locationScore;
    private double hotelAndLocationScore;
    private double convenienceScore;
    private double journeyScore;
    private double tripScore;

    public TripFuzzyDTO (
            @Nonnull final TripPureDTO tripPureDTO,
            @Nonnull final TripFuzzEvaluationDTO tripFuzzEvaluationDTO) {
        this.id = tripPureDTO.getId();
        this.price = tripPureDTO.getPrice();
        this.location = tripPureDTO.getLocation();
        this.hotelId = tripPureDTO.getHotelId();
        this.hotel = tripPureDTO.getHotel();
        this.stars = tripPureDTO.getStars();
        this.foodPackage = tripPureDTO.getFoodPackage();
        this.airport = tripPureDTO.getAirport();
        this.beachDist = tripPureDTO.getBeachDist();
        this.familyFriendly = tripPureDTO.isFamilyFriendly();
        this.internet = tripPureDTO.isInternet();
        this.swimmingPool = tripPureDTO.isSwimmingPool();
        this.averageRating = tripPureDTO.getAverageRating();
        this.dateFrom = tripPureDTO.getDateFrom();
        this.dateTo = tripPureDTO.getDateTo();
        this.days = tripPureDTO.getDays();
        this.ratingAttributesScore = tripFuzzEvaluationDTO.getRatingAttributes();
        this.equipmentScore = tripFuzzEvaluationDTO.getEquipment();
        this.hotelScore = tripFuzzEvaluationDTO.getHotel();
        this.locationScore = tripFuzzEvaluationDTO.getLocation();
        this.hotelAndLocationScore = tripFuzzEvaluationDTO.getHotelAndLocation();
        this.convenienceScore = tripFuzzEvaluationDTO.getConvenience();
        this.journeyScore = tripFuzzEvaluationDTO.getJourney();
        this.tripScore = tripFuzzEvaluationDTO.getTrip();
    }

}
