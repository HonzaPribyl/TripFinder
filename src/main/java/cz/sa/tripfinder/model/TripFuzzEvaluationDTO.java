package cz.sa.tripfinder.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TripFuzzEvaluationDTO {
    private double ratingAttributes;
    private double equipment;
    private double hotel;
    private double location;
    private double hotelAndLocation;
    private double convenience;
    private double journey;
    private double trip;
}
